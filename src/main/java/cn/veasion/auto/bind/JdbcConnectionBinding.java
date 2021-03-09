package cn.veasion.auto.bind;

import cn.veasion.auto.bind.base.Binding;
import cn.veasion.auto.core.ResultProxy;
import cn.veasion.auto.util.Api;
import cn.veasion.auto.util.JdbcDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * JdbcConnectionBinding
 *
 * @author luozhuowei
 * @date 2020/12/4
 */
@SuppressWarnings("unused")
@Api.ClassInfo(desc = "数据库连接")
public class JdbcConnectionBinding implements JavaScriptBinding<Connection> {

    private Binding<Connection> binding;

    @ResultProxy(value = false)
    @Api(value = "列表查询")
    public List<Map<String, Object>> query(String sql, Object[] params) throws SQLException {
        return JdbcDao.query(binding.getBean(), sql, params);
    }

    @ResultProxy(value = false)
    @Api(value = "获取单个值")
    public Object queryOnly(String sql, Object[] params) throws SQLException {
        return JdbcDao.queryOnly(binding.getBean(), sql, params);
    }

    @ResultProxy(value = false)
    @Api(value = "执行增删改")
    public int update(String sql, Object[] params) throws SQLException {
        return JdbcDao.executeUpdate(binding.getBean(), sql, params);
    }

    @ResultProxy(value = false)
    @Api(value = "执行新增，返回自增长id")
    public Long insert(String sql, Object[] params) throws SQLException {
        return JdbcDao.executeInsert(binding.getBean(), sql, params);
    }

    @ResultProxy(value = false)
    @Api(value = "执行DDL")
    public int executeDDL(String sql) throws SQLException {
        return JdbcDao.executeUpdate(binding.getBean(), sql);
    }

    @ResultProxy(value = false, log = false)
    @Api(value = "关闭连接")
    public void close() {
        JdbcDao.closeConnection(binding.getBean());
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public void setBinding(Binding<Connection> binding) {
        this.binding = binding;
    }

    @Override
    @Api(generator = false)
    @ResultProxy(value = false, log = false)
    public Binding<Connection> getBinding() {
        return binding;
    }
}
