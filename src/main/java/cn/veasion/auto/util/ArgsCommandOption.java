package cn.veasion.auto.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ArgsCommandOption
 *
 * @author luozhuowei
 * @date 2021/2/22
 */
public class ArgsCommandOption {

    private Map<String, List<String>> optionMap;

    public boolean hasOption(String option) {
        return optionMap.containsKey(option);
    }

    public String getOneOption(String option) {
        List<String> list = optionMap.get(option);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public String getOption(String option) {
        List<String> list = optionMap.get(option);
        if (list == null || list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        } else {
            return String.join(" ", list);
        }
    }

    public List<String> getOptions(String option) {
        return optionMap.get(option);
    }

    public String getOptionOrDefault(String option, String defaultValue) {
        String val = getOption(option);
        return val != null ? val : defaultValue;
    }

    public static ArgsCommandOption parse(String... args) {
        ArgsCommandOption option = new ArgsCommandOption();
        option.optionMap = new HashMap<>(args.length);
        if (args.length == 0) {
            return option;
        }
        String key = null;
        for (String arg : args) {
            if (arg == null) {
                continue;
            }
            arg = arg.trim();
            if ("".equals(arg)) {
                continue;
            }
            if (arg.startsWith("-") && arg.length() > 1) {
                String sub = arg.substring(1);
                String[] split = sub.split("");
                if (split.length > 1) {
                    for (String s : split) {
                        option.optionMap.put(s, null);
                    }
                }
                key = sub;
                option.optionMap.put(key, null);
            } else if (key != null) {
                final String val = arg;
                option.optionMap.compute(key, (k, v) -> {
                    if (v == null) {
                        v = new ArrayList<>();
                    }
                    v.add(val);
                    return v;
                });
            }
        }
        return option;
    }

}
