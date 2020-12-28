package cn.veasion.auto.debug;

import com.alibaba.fastjson.JSONObject;
import cn.veasion.auto.bind.WebElementBinding;
import cn.veasion.auto.util.ApiCodeTips;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MenuHandler
 *
 * @author luozhuowei
 * @date 2020/8/4
 */
public class MenuHandler implements SocketHandler {

    private static final String TYPE = "menu";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void accept(JSONObject jsonObject, ChannelHandlerContext ctx) {
        List<ApiCodeTips.Tips> menusTips = ApiCodeTips.getMenusTips();
        List<Menu> list = new ArrayList<>(menusTips.size() + 10);
        addExtMenu(list);
        for (ApiCodeTips.Tips menusTip : menusTips) {
            list.add(new Menu(menusTip));
        }
        list.sort((v1, v2) -> {
            if (v1.sort != v2.sort) {
                return Integer.compare(v1.sort, v2.sort);
            } else {
                return Integer.compare(v1.tips.length(), v2.tips.length());
            }
        });
        ctx.channel().writeAndFlush(Debug.newSocketResult(jsonObject, TYPE, list));
    }

    /**
     * 自定义扩展菜单
     */
    private static void addExtMenu(List<Menu> list) {
        list.add(new Menu("target", "let target = $", "元素target"));
        list.add(new Menu("if", "if (findOne($)) {\n    \n}", "IF判断"));
        list.add(new Menu("assert", "assertResult(findOne($) != null, \"xxx不存在\")", "断言元素是否存在"));
        list.add(new Menu("for", "let list ＝ find($);\nfor(let i in list) {\n    let element ＝ list[i];\n}", "FOR循环"));
        List<ApiCodeTips.Tips> elementClassTips = ApiCodeTips.getClassTips(WebElementBinding.class.getSimpleName());
        if (elementClassTips != null && !elementClassTips.isEmpty()) {
            list.add(new Menu("findOne ->", "findOne($)", elementClassTips.stream().filter(o -> o.codeTips.endsWith("()")).collect(Collectors.toList())));
        }
    }

    public static class Menu implements Serializable {

        private static final long serialVersionUID = 1L;

        private String tips;
        private String code;
        private String desc;
        private int sort;
        private List<Menu> children;

        Menu(String tips, String code, String desc) {
            this.sort = 1;
            this.tips = tips;
            this.code = code;
            this.desc = desc;
        }

        Menu(String tips, String code, List<ApiCodeTips.Tips> childrenTips) {
            this.sort = 1;
            this.tips = tips;
            this.code = code;
            this.desc = tips;
            if (childrenTips != null && !childrenTips.isEmpty()) {
                this.children = new ArrayList<>(childrenTips.size());
                for (ApiCodeTips.Tips childrenTip : childrenTips) {
                    this.children.add(new Menu(childrenTip));
                }
            }
        }

        Menu(ApiCodeTips.Tips tips) {
            this.sort = 2;
            this.tips = tips.codeTips;
            this.desc = tips.api != null ? tips.api.value() : "";
            this.code = tips.menuCode != null ? tips.menuCode : tips.codeTips;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public List<Menu> getChildren() {
            return children;
        }

        public void setChildren(List<Menu> children) {
            this.children = children;
        }
    }
}
