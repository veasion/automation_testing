package cn.veasion.auto.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CalculatorUtils
 *
 * @author luozhuowei
 * @date 2020/10/28
 */
public class CalculatorUtils {

    private static final int SCALE = 4;
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;
    private static final Pattern OPERATOR = Pattern.compile("[+\\-*/×÷%√^]");

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
        testCalculate("4.99+(5.99+6.99)*1.06^2+√4");
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
        for (int i = 0; i < n && n <= 10; i++) {
            pattern = pattern.concat("#");
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
                    sb.append(flatCalculate(group.getContext()));
                }
            } else {
                sb.append(group.getContext());
            }
        }
        return flatCalculate(sb.toString());
    }

    private static BigDecimal flatCalculate(final String eval) {
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
        return simpleCalculate(pre, maxLevel);
    }

    private static BigDecimal simpleCalculate(ValueLink link, int maxLevel) {
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
        POW("^", 3, false, (a, b) -> BigDecimal.valueOf(Math.pow(a.doubleValue(), b.doubleValue()))),
        SQRT("√", 3, true, (a, b) -> {
            if (a != null) {
                // 开N次方
                return BigDecimal.valueOf(Math.pow(b.doubleValue(), BigDecimal.ONE.divide(a, SCALE, ROUNDING_MODE).doubleValue()));
            } else {
                // 开平方
                return BigDecimal.valueOf(Math.sqrt(b.doubleValue()));
            }
        });

        private int level;
        private String op1;
        private String op2;
        private boolean leftNullable;
        private BinaryOperator<BigDecimal> function;

        Operator(String op1, int level, boolean leftNullable, BinaryOperator<BigDecimal> function) {
            this(op1, null, level, leftNullable, function);
        }

        Operator(String op1, String op2, int level, boolean leftNullable, BinaryOperator<BigDecimal> function) {
            this.level = level;
            this.op2 = op2;
            this.leftNullable = leftNullable;
            this.op1 = Objects.requireNonNull(op1);
            this.function = Objects.requireNonNull(function);
        }

        public BigDecimal apply(BigDecimal a, BigDecimal b) {
            if (b == null) {
                throw new AutomationException(String.format("%s 后面必须位数字", op1));
            }
            if (a == null && !leftNullable) {
                throw new AutomationException(String.format("格式错误：%s %s", op1, String.valueOf(b)));
            }
            return function.apply(a, b);
        }

        public static Operator of(String operator) {
            if (operator == null) {
                return null;
            }
            for (Operator value : values()) {
                if (operator.equals(value.op1) || operator.equals(value.op2)) {
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