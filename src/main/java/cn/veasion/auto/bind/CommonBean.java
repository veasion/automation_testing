package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.AbstractInitializingBean;
import cn.veasion.auto.core.JavaScriptCore;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.debug.Debug;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.AssertException;
import cn.veasion.auto.util.CalculatorUtils;
import cn.veasion.auto.util.JavaScriptUtils;
import cn.veasion.auto.util.JdbcDao;
import cn.veasion.auto.util.PinyinUtils;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.NativeDate;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * CommonBean
 *
 * @author luozhuowei
 * @date 2021/3/9
 */
@SuppressWarnings({"unused", "restriction"})
@Api.ClassInfo(value = "common", root = true)
public class CommonBean extends AbstractInitializingBean {

    private static final Random RAND = new Random(System.currentTimeMillis());

    @ResultProxy
    @Api(value = "数据库连接", result = java.sql.Connection.class)
    public Object createJdbcConnection(String jdbcUrl, String user, String password) throws SQLException {
        return JdbcDao.createConnection(jdbcUrl, user, password);
    }

    @ResultProxy
    @Api(value = "Mysql数据库连接", result = java.sql.Connection.class)
    public Object createMysqlConnection(String ip, int port, String database, String user, String password) throws SQLException {
        return JdbcDao.createConnection(JdbcDao.MYSQL_EVAL_URL, ip, port, database, user, password);
    }

    @ResultProxy(value = false, log = false)
    @Api(value = "输入")
    public String input(String title) {
        return Debug.input(title, null);
    }

    @ResultProxy
    @Api("暂停多少毫秒")
    public void pause(long millis) {
        sleep(millis);
    }

    @ResultProxy
    @Api("暂停多少毫秒")
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOGGER.error("sleep", e);
            Thread.currentThread().interrupt();
        }
    }

    @Api("格式化时间")
    @ResultProxy(value = false, log = false)
    public String formatDate(Object date, String pattern) {
        Date newDate = null;
        if (date instanceof NativeDate) {
            newDate = new Date((long) NativeDate.getTime(date));
        } else if (date instanceof Date) {
            newDate = (Date) date;
        } else if (date instanceof Number) {
            newDate = new Date(((Number) date).longValue());
        } else if (date instanceof ScriptObjectMirror) {
            ScriptObjectMirror obj = (ScriptObjectMirror) date;
            if ("Date".equalsIgnoreCase(obj.getClassName())) {
                double time = (double) obj.callMember("getTime");
                newDate = new Date((long) time);
            }
        }
        if (newDate == null) {
            return String.valueOf(date);
        }
        return new SimpleDateFormat(pattern).format(newDate);
    }

    @Api("随机字符串")
    @ResultProxy(value = false, log = false)
    public String randCode(Integer length) {
        if (length == null) {
            length = 8;
        }
        StringBuilder sb = new StringBuilder();
        while (length > 0) {
            if (length >= 8) {
                length -= 8;
                sb.append(String.format("%08d", RAND.nextInt(100000000)));
            } else {
                length -= 1;
                sb.append(RAND.nextInt(10));
            }
        }
        return sb.toString();
    }

    @Api("控制台打印")
    @ResultProxy(value = false, log = false)
    public void println(Object message, @Api.Param(allowNone = true) Object... args) {
        String str;
        if (message == null) {
            str = "null";
        } else if (message instanceof String) {
            str = message.toString();
        } else {
            StringBuilder sb = new StringBuilder();
            JavaScriptUtils.appendObject(sb, message);
            str = sb.toString();
        }
        if (args != null && args.length == 1 && JavaScriptUtils.isNull(args[0])) {
            JavaScriptUtils.println(str);
        } else if (args != null && args.length > 0) {
            JavaScriptUtils.println(String.format(str, args));
        } else {
            JavaScriptUtils.println(str);
        }
    }

    @Api("计算")
    @ResultProxy(value = false, log = false)
    public String calculate(@Api.Param(desc = "运算式") String str, @Api.Param(desc = "保留几位小数") int n) {
        return CalculatorUtils.calculate(str, n);
    }

    @Api("断言")
    @ResultProxy(value = false)
    public void assertResult(boolean flag, Object message) {
        String msg = String.valueOf(message);
        if (!flag) {
            LOGGER.error("断言未通过: {}", msg);
            throw new AssertException(msg);
        }
    }

    @Api("拼音")
    @ResultProxy(value = false)
    public String pinyin(String chinese) {
        return PinyinUtils.getChineseAllWord(chinese);
    }

    @Api("info")
    @ResultProxy(value = false)
    public String info() {
        return JavaScriptCore.getCurrentJsInfo();
    }

}
