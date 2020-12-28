package cn.veasion.auto.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CalculatorUtils
 *
 * @author luozhuowei
 * @date 2020/10/28
 */
public class CalculatorUtils {

    private static final Pattern OPERATOR = Pattern.compile("[+\\-*/×÷%√^]");
    private static final int SCALE = 4, ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    public static void main(String[] args) {
        testCalculate("-2");
        testCalculate("√8");
        testCalculate("2^3");
        testCalculate("10%6");
        testCalculate("1+(-4)");
        testCalculate("√(3*3)");
        testCalculate("2×3÷3");
        testCalculate("1+3√(3*3*3)");
        testCalculate("2+5*2-6/3");
        testCalculate("(1+2+3)^2+4");
        testCalculate("2+5*(2-6*(3+1))/3");
        testCalculate("4.99+(5.99+6.99)*1.06^2");
    }

    private static BigDecimal testCalculate(String str) {
        // 计算结果
        BigDecimal result = calculate(str);
        // 保留两位小数
        System.out.println(str + "=" + decimalFormat(result, 2));
        return result;
    }

    /**
     * 计算（保留N位小数）
     *
     * @param str 运算式
     * @param n   保留N位小数
     */
    public static String calculate(String str, int n) {
        BigDecimal result = calculate(str);
        return decimalFormat(result, n);
    }

    /**
     * 计算
     *
     * @param str 运算式
     */
    public static BigDecimal calculate(String str) {
        try {
            return calcGroups(SplitGroupUtils.group(str, "(", ")", true));
        } catch (Exception e) {
            throw new AutomationException(String.format("计算异常：%s", str), e);
        }
    }

    /**
     * 格式化
     *
     * @param value 数值
     * @param n     保留N位小数
     */
    public static String decimalFormat(BigDecimal value, int n) {
        if (value == null) {
            return null;
        }
        String pattern = n > 0 ? "#." : "#";
        if (n > 10) {
            n = 10;
        }
        for (int i = 0; i < n; i++) {
            pattern += "#";
        }
        return new DecimalFormat(pattern).format(value);
    }

    private static BigDecimal calcGroups(List<SplitGroupUtils.Group> list) {
        StringBuilder sb = new StringBuilder();
        for (SplitGroupUtils.Group group : list) {
            if (group.getType() == 1) {
                if (group.getChildren() != null) {
                    sb.append(calcGroups(group.getChildren()));
                } else {
                    sb.append(flatCalc(group.getContext()));
                }
            } else {
                sb.append(group.getContext());
            }
        }
        return flatCalc(sb.toString());
    }

    private static BigDecimal flatCalc(final String eval) {
        String str = eval;
        if (str == null || "".equals(str = str.trim())) {
            return null;
        }
        int start = 0, maxLevel = -1;
        Matcher matcher = OPERATOR.matcher(str);
        ValueLink valueLink, pre = null;
        while (matcher.find()) {
            String group = matcher.group().trim();
            String leftVal = str.substring(start, matcher.start()).trim();
            Operator operator = Operator.of(group);
            BigDecimal val = "".equals(leftVal) ? null : new BigDecimal(leftVal);
            if (operator == null) {
                throw new AutomationException("不支持运算符: " + group);
            } else if (val == null && !operator.leftNullable) {
                throw new AutomationException("不支持单独运算：" + group);
            }
            valueLink = new ValueLink(val, operator, null);
            valueLink.pre = pre;
            if (pre != null) {
                pre.next = valueLink;
                pre.right = valueLink.left;
            }
            pre = valueLink;
            if (operator.level > maxLevel) {
                maxLevel = operator.level;
            }
            start = matcher.end();
        }
        if (pre == null) {
            return new BigDecimal(str);
        }
        if (start < str.length()) {
            String endStr = str.substring(start).trim();
            pre.right = new BigDecimal(endStr);
        } else {
            throw new AutomationException("不能以运算符结尾：" + eval);
        }
        while (true) {
            if (pre.left == null && pre.pre != null && pre.pre.right == null) {
                pre.pre.right = pre.operator.apply(null, pre.right);
                pre.pre.next = pre.next;
            }
            if (pre.pre == null) {
                break;
            }
            pre = pre.pre;
        }
        return calculate(pre, maxLevel);
    }

    private static BigDecimal calculate(ValueLink link, int maxLevel) {
        while (true) {
            while (link.pre != null) {
                link = link.pre;
            }
            if (link.next == null) {
                break;
            }
            int newMaxLevel = -1;
            while (true) {
                if (link.operator.level >= maxLevel) {
                    BigDecimal val = link.operator.apply(link.left, link.right);
                    if (link.pre != null) {
                        link.pre.right = val;
                        link.pre.next = link.next;
                    }
                    if (link.next != null) {
                        link.next.left = val;
                        link.next.pre = link.pre;
                    }
                } else if (link.operator.level > newMaxLevel) {
                    newMaxLevel = link.operator.level;
                }
                if (link.next != null) {
                    link = link.next;
                } else {
                    maxLevel = newMaxLevel;
                    break;
                }
            }
        }
        return link.operator.apply(link.left, link.right);
    }

    enum Operator {

        ADD("+", 1, false, BigDecimal::add),
        SUBTRACT("-", 1, true, (a, b) -> a == null ? b.negate() : a.subtract(b)),
        MULTIPLY("*", "×", 2, false, BigDecimal::multiply),
        DIVIDE("/", "÷", 2, false, (a, b) -> a.divide(b, SCALE, ROUNDING_MODE)),
        DIVIDE_REMAINDER("%", 2, false, (a, b) -> a.divideAndRemainder(b)[1]),
        POW("^", 3, false, (a, b) -> new BigDecimal(Math.pow(a.doubleValue(), b.doubleValue()))),
        SQRT("√", 3, true, (a, b) -> {
            if (a != null) {
                // 开N次方
                return new BigDecimal(Math.pow(b.doubleValue(), BigDecimal.ONE.divide(a, SCALE, ROUNDING_MODE).doubleValue()));
            } else {
                // 开平方
                return new BigDecimal(Math.sqrt(b.doubleValue()));
            }
        });

        private int level;
        private String operator;
        private String operator2;
        private boolean leftNullable;
        private BiFunction<BigDecimal, BigDecimal, BigDecimal> function;

        Operator(String operator, int level, boolean leftNullable, BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
            this(operator, null, level, leftNullable, function);
        }

        Operator(String operator, String operator2, int level, boolean leftNullable, BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
            this.level = level;
            this.operator2 = operator2;
            this.leftNullable = leftNullable;
            this.operator = Objects.requireNonNull(operator);
            this.function = Objects.requireNonNull(function);
        }

        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            if (b == null) {
                throw new AutomationException(String.format("%s 后面必须位数字", operator));
            }
            if (a == null && !leftNullable) {
                throw new AutomationException(String.format("格式错误：%s %s", operator, String.valueOf(b)));
            }
            return function.apply(a, b);
        }

        public static Operator of(String operator) {
            if (operator == null) {
                return null;
            }
            for (Operator value : values()) {
                if (operator.equals(value.operator) || operator.equals(value.operator2)) {
                    return value;
                }
            }
            return null;
        }
    }

    static class ValueLink {
        ValueLink pre;
        BigDecimal left;
        Operator operator;
        BigDecimal right;
        ValueLink next;

        ValueLink(BigDecimal left, Operator operator, BigDecimal right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }
    }
}
