package cn.veasion.auto.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JdbcDao
 *
 * @author luozhuowei
 * @date 2020/12/4
 */
public class JdbcDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDao.class);

    private static boolean mapUnderscoreToCamelCase = true;
    private static final Pattern LINE_PATTERN = Pattern.compile("[-_](\\w)");
    private static final Map<String, Object> FIELD_CACHE_MAP = new HashMap<>();
    public static final String MYSQL_EVAL_URL = "jdbc:mysql://${ip}:${port}/${database}?useUnicode=true&characterEncoding=utf-8&autoReconnect=true";

    /**
     * 是否开启驼峰转换
     */
    public static boolean isMapUnderscoreToCamelCase() {
        return mapUnderscoreToCamelCase;
    }

    /**
     * 设置是否开启驼峰转换
     */
    public static void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        JdbcDao.mapUnderscoreToCamelCase = mapUnderscoreToCamelCase;
    }

    /**
     * 创建连接
     */
    public static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 创建连接
     */
    public static Connection createConnection(String evalUrl, String ip, int port, String database, String user, String password) throws SQLException {
        String url = evalUrl.replace("${ip}", ip).replace("${port}", String.valueOf(port)).replace("${database}", database);
        return createConnection(url, user, password);
    }

    /**
     * 关闭连接
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行增删改
     *
     * @return 返回影响条数
     */
    public static int executeUpdate(Connection conn, String sql, Object... params) throws SQLException {
        int count;
        PreparedStatement ps = null;
        try {
            ps = prepareStatement(conn, sql, params);
            count = ps.executeUpdate();
        } finally {
            closeAll(ps, null);
        }
        return count;
    }

    /**
     * 执行新增，返回自增长id
     *
     * @return 返回自增长id
     */
    public static Long executeInsert(Connection conn, String sql, Object... params) throws SQLException {
        Long id = null;
        PreparedStatement ps = null;
        ResultSet result = null;
        try {
            ps = prepareStatement(conn, sql, params);
            int count = ps.executeUpdate();
            if (count >= 1) {
                result = ps.getGeneratedKeys();
                if (result != null && result.next()) {
                    id = result.getLong(1);
                }
            }
        } finally {
            closeAll(ps, result);
        }
        return id;
    }

    /**
     * 列表查询
     *
     * @return 返回列表数据
     */
    public static List<Map<String, Object>> query(Connection conn, String sql, Object... params) throws SQLException {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            String columnName;
            Map<String, Object> map;
            ps = prepareStatement(conn, sql, params);
            rs = ps.executeQuery();
            ResultSetMetaData data;
            while (rs.next()) {
                map = new HashMap<>();
                data = rs.getMetaData();
                int count = data.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    columnName = data.getColumnLabel(i);
                    if (mapUnderscoreToCamelCase) {
                        columnName = lineToHump(columnName);
                    }
                    map.put(columnName, rs.getObject(i));
                }
                list.add(map);
            }
        } finally {
            closeAll(ps, rs);
        }
        return list;
    }

    /**
     * 列表查询
     *
     * @return 返回列表数据
     */
    public static <T> List<T> query(Connection conn, String sql, Class<T> clazz, Object... params) throws Exception {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<T> list = new ArrayList<>();
        try {
            T bean;
            String columnName = null;
            ps = prepareStatement(conn, sql, params);
            rs = ps.executeQuery();
            ResultSetMetaData data;
            while (rs.next()) {
                data = rs.getMetaData();
                bean = clazz.newInstance();
                int count = data.getColumnCount();
                try {
                    for (int i = 1; i <= count; i++) {
                        columnName = data.getColumnLabel(i);
                        if (mapUnderscoreToCamelCase) {
                            columnName = lineToHump(columnName);
                        }
                        setFiled(bean, columnName, rs.getObject(i));
                    }
                } catch (Exception e) {
                    LOGGER.error("字段映射失败！{}#{}", bean.getClass().getName(), columnName, e);
                    throw e;
                }
                list.add(bean);
            }
        } finally {
            closeAll(ps, rs);
        }
        return list;
    }

    /**
     * 获取单个值
     *
     * @return 返回结果
     */
    public static Object queryOnly(Connection conn, final String sql, Object... params) throws SQLException {
        Object value = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = prepareStatement(conn, sql, params);
            rs = ps.executeQuery();
            if (rs != null && rs.next()) {
                value = rs.getObject(1);
            }
        } finally {
            closeAll(ps, rs);
        }
        return value;
    }

    private static PreparedStatement prepareStatement(Connection conn, String sql, Object... params) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            LOGGER.info("执行SQL: {}, 参数: {}", sql, params);
        } else {
            LOGGER.info("执行SQL: {}", sql);
        }
        return ps;
    }

    private static void closeAll(Statement ps, ResultSet rs) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean setFiled(Object object, String fieldName, Object value) throws Exception {
        String key = object.getClass().getName() + "." + fieldName;
        // cache
        if (FIELD_CACHE_MAP.containsKey(key)) {
            try {
                Object cache = FIELD_CACHE_MAP.get(key);
                if (cache instanceof Method) {
                    ((Method) cache).invoke(object, value);
                    return true;
                } else if (cache instanceof Field) {
                    Field field = (Field) cache;
                    field.setAccessible(true);
                    field.set(object, value);
                    return true;
                }
            } catch (Exception e) {
                FIELD_CACHE_MAP.remove(key);
            }
        }
        // method
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equalsIgnoreCase("set" + fieldName) &&
                    method.getParameterCount() == 1 &&
                    Modifier.isPublic(method.getModifiers()) &&
                    !Modifier.isFinal(method.getModifiers()) &&
                    method.getParameterTypes()[0].isAssignableFrom(value.getClass())) {
                method.invoke(object, value);
                FIELD_CACHE_MAP.put(key, method);
                return true;
            }
        }
        // field
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName) &&
                    !Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                field.set(object, value);
                FIELD_CACHE_MAP.put(key, field);
                return true;
            }
        }
        return false;
    }

    private static String lineToHump(String str) {
        Matcher matcher = LINE_PATTERN.matcher(str.trim().toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        if (sb.length() == 0) {
            return str;
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        Connection connection = JdbcDao.createConnection(MYSQL_EVAL_URL, "127.0.0.1", 3306, "order", "root", "123456");
        String sql = "select order_code as orderCode, user_name, order_amount from order where order_status = ? limit 10";
        List<Map<String, Object>> list = JdbcDao.query(connection, sql, 9000);
        list.forEach(System.out::println);
        JdbcDao.closeConnection(connection);
    }

}