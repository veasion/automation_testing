package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSONObject;
import cn.veasion.auto.util.ApiCodeTips;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * CodeTipsHandler
 *
 * @author luozhuowei
 * @date 2020/8/2
 */
public class CodeTipsHandler implements SocketHandler {

    private static final String TYPE = "codeTips";
    private static final Map<String, Map<String, String>> resultClassList = new HashMap<>();
    private static final Map<String, BiFunction<String, String, String>> endWithList = new HashMap<>();

    static {
        endWithList.put("if", (endWith, resultClass) -> "if ($code) {\n    \n}");
        endWithList.put("var", (endWith, resultClass) -> "let " + getVar(endWith) + " = $code;");
        endWithList.put("assert", (endWith, resultClass) -> {
            if ("string".equals(resultClass)) {
                return "assertResult(!!$code, \"字符串为空\");";
            } else if (resultClass != null && resultClass.endsWith("[]")) {
                return "let $var = $code;\nassertResult($var != null && $var.length > 0, \"元素不存在\");";
            } else {
                return "assertResult($code != null, \"元素不存在\");";
            }
        });
        addExtResultClass("WebElementBinding[]", "for", "let $var = $code;\nfor (let i = 0; i < $var.length; i++) \n{    \n}");
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void accept(JSONObject jsonObject, ChannelHandlerContext ctx) {
        String jsCodeLine = jsonObject.getString("data");
        if (jsCodeLine != null) {
            String code = handleCode(jsCodeLine);
            ApiCodeTips.SearchCallback searchCallback = new ApiCodeTips.SearchCallback();
            Set<ApiCodeTips.Tips> search = ApiCodeTips.search(code, searchCallback);
            if (search == null) {
                search = new HashSet<>();
            }
            List<CodeTips> list = new ArrayList<>();
            addExtTips(jsCodeLine, list, searchCallback);
            if (!search.isEmpty()) {
                for (ApiCodeTips.Tips tips : search) {
                    list.add(new CodeTips(tips.codeTips, handleCodeTips(code, tips.codeTips), tips.api != null ? tips.api.value() : "", 2));
                }
            }
            if (!list.isEmpty()) {
                list.sort((v1, v2) -> {
                    if (v1.sort != v2.sort) {
                        return Integer.compare(v1.sort, v2.sort);
                    } else {
                        return Integer.compare(v1.codeTips.length(), v2.codeTips.length());
                    }
                });
                ctx.channel().writeAndFlush(Debug.newSocketResult(jsonObject, TYPE, list));
            }
        }
    }

    private static void addExtTips(String jsCodeLine, List<CodeTips> list, ApiCodeTips.SearchCallback searchCallback) {
        if (searchCallback.skip) {
            return;
        }
        String[] split = jsCodeLine.split(";");
        String splitCode = null;
        String code = split[split.length - 1];
        if (split.length > 1) {
            splitCode = jsCodeLine.substring(0, jsCodeLine.length() - code.length());
        }
        int index = code.lastIndexOf(".");
        if (index <= 0) return;
        String endWith = code.substring(index + 1);
        for (String key : endWithList.keySet()) {
            if (endWith.startsWith(key)) {
                String value = endWithList.get(key).apply(endWith.substring(key.length()), searchCallback.resultClassName);
                list.add(buildExtCodeTips(splitCode, code.substring(0, index), key, value));
            } else if (key.startsWith(endWith)) {
                String value = endWithList.get(key).apply(null, searchCallback.resultClassName);
                list.add(buildExtCodeTips(splitCode, code.substring(0, index), key, value));
            }
        }
        if (searchCallback.resultClassName != null) {
            Map<String, String> map = resultClassList.get(searchCallback.resultClassName);
            if (map != null) {
                boolean allMatches = "".equals(endWith);
                for (String key : map.keySet().stream().filter(k -> allMatches || k.startsWith(endWith)).collect(Collectors.toList())) {
                    String value = map.get(key);
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                    list.add(buildExtCodeTips(splitCode, code.substring(0, index), key, value));
                }
            }
        }
    }

    private static CodeTips buildExtCodeTips(String splitCode, String code, String key, String value) {
        value = value.replace("$code", code).replace("$var", getVar(null));
        if (splitCode != null) {
            value = splitCode + "\n" + value;
        }
        return new CodeTips(key, value, key, 1, true);
    }

    private static void addExtResultClass(String resultClass, String tips, String code) {
        resultClassList.compute(resultClass, (k, v) -> {
            if (v == null) {
                v = new HashMap<>();
            }
            v.put(tips, code);
            return v;
        });
    }

    private static String handleCode(String jsCodeLine) {
        int f = jsCodeLine.lastIndexOf(";");
        int k = jsCodeLine.lastIndexOf(" ");
        int sub = Math.max(f, k);
        if (sub > -1) {
            jsCodeLine = jsCodeLine.substring(sub + 1);
        }
        int sd = jsCodeLine.indexOf(".");
        int ed = jsCodeLine.lastIndexOf(".");
        if (sd != ed) {
            sd = jsCodeLine.lastIndexOf(".", ed - 1);
            jsCodeLine = jsCodeLine.substring(sd + 1);
        }
        return jsCodeLine.trim();
    }

    private static String handleCodeTips(String code, String codeTips) {
        if (code.endsWith(".")) {
            return codeTips;
        }
        int index = code.lastIndexOf(".");
        if (index != -1) {
            code = code.substring(index + 1);
        }
        if (codeTips.startsWith(code)) {
            return codeTips.substring(code.length());
        } else {
            return codeTips;
        }
    }

    private static String getVar(String var) {
        if (var == null || "".equals(var.trim())) {
            var = "var" + (int) (Math.random() * 1000 + 1);
        }
        return var;
    }

    public static class CodeTips implements Serializable {

        private static final long serialVersionUID = 1L;

        int sort;
        private String codeTips;
        private String autoCode;
        private String desc;
        private boolean line;

        CodeTips(String codeTips, String autoCode, String desc, int sort, boolean line) {
            this(codeTips, autoCode, desc, sort);
            this.line = line;
        }

        CodeTips(String codeTips, String autoCode, String desc, int sort) {
            this.codeTips = codeTips;
            this.autoCode = autoCode;
            this.desc = desc;
            this.sort = sort;
        }

        public String getCodeTips() {
            return codeTips;
        }

        public void setCodeTips(String codeTips) {
            this.codeTips = codeTips;
        }

        public String getAutoCode() {
            return autoCode;
        }

        public void setAutoCode(String autoCode) {
            this.autoCode = autoCode;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public boolean isLine() {
            return line;
        }

        public void setLine(boolean line) {
            this.line = line;
        }
    }

}
