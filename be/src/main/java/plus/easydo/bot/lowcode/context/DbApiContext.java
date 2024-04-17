package plus.easydo.bot.lowcode.context;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import com.yomahub.liteflow.context.ContextBean;
import plus.easydo.bot.util.FlexDataSourceUtil;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author yuzhanfeng
 * @Date 2024/4/16
 * @Description 数据操作上下文
 */
@ContextBean("dbApi")
@ContextBeanDesc("数据库api")
public class DbApiContext {



    /**
     * 根据主键查询一条数据
     *
     * @param tableName  数据库表名
     * @param primaryKey 主键名
     * @param idValue    主键值
     * @return 返回查询到的数据行
     */
    @ContextBeanMethodDesc("根据主键查询一条数据")
    public Map<String, Object> selectOneById(String tableName, String primaryKey, Object idValue) {
        return  Db.selectOneById(tableName,primaryKey,idValue).toCamelKeysMap();
    }

    /**
     * 查询所有数据
     *
     * @param tableName 数据库表名
     * @return 返回包含所有数据的List<Row>对象
     */
    @ContextBeanMethodDesc("查询所有数据")
    public List<Row> selectAll(String tableName) {
        return Db.selectAll(tableName);
    }

    /**
     * 执行插入Sql操作
     *
     * @param sql Sql语句，其中?表示占位符
     * @param args Sql语句中的参数，可以为任意数量
     * @return 插入影响的行数
     */
    @ContextBeanMethodDesc("执行插入Sql, sql: ?号占位 args:任意数量变量 ")
    public int insertBySql(String sql, Object... args) {
        return Db.insertBySql(sql,args);
    }

    /**
     * 执行更新Sql操作
     *
     * @param sql Sql语句，其中?表示占位符
     * @param args Sql语句中的参数，可以为任意数量
     * @return 更新影响的行数
     */
    @ContextBeanMethodDesc("执行更新Sql, sql: ?号占位 args:任意数量变量 ")
    public int updateBySql(String sql, Object... args) {
        return Db.updateBySql(sql,args);
    }

    /**
     * 执行删除Sql操作
     *
     * @param sql Sql语句，其中?表示占位符
     * @param args Sql语句中的参数，可以为任意数量
     * @return 删除影响的行数
     */
    @ContextBeanMethodDesc("执行删除sql, sql: ?号占位 args:任意数量变量 ")
    public int deleteBySql(String sql, Object... args) {
        return Db.deleteBySql(sql,args);
    }


    /**
     * 执行任意sql语句，并返回执行结果是否为true
     *
     * @param sql 要执行的sql语句
     * @return 执行结果，如果执行成功返回true，否则返回false
     */
    @ContextBeanMethodDesc("执行任意sql,如果执行成功返回true")
    public boolean executeSql(String sql) {
        DruidDataSource dataSource =  SpringUtil.getBean(FlexDataSourceUtil.class).getFirstDataSource();
        try {
            Connection conn = dataSource.getConnection();
            Statement stat = conn.createStatement();
            boolean result = stat.execute(sql);
            stat.close();
            conn.close();
            return result;
        }catch (Exception e) {
            return false;
        }
    }

}
