package com.wulang.rename.config;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;
import com.alibaba.druid.util.StringUtils;
import com.wulang.rename.util.TableNameFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),})
public class RerouteToTableInterceptor implements Interceptor {
    private Map map;
    private Set<String> tableSet;
    public static boolean openInterceptor = false;

    public RerouteToTableInterceptor() {
        //标识使用了该插件
        setOpenInterceptor(true);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object parameterObject = args[1];
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String sql = boundSql.getSql();
        MySqlSchemaStatVisitor visitor = getMySqlSchemaStatVisitor(sql);
        Map<TableStat.Name, TableStat> tableMap = visitor.getTables();
        for (Map.Entry<TableStat.Name, TableStat> next : tableMap.entrySet()) {
            TableStat.Name key = next.getKey();
            String tableName = key.toString();
            String vaule = TableNameFactory.getValue(tableName);
            if (!StringUtils.isEmpty(vaule)) {
                sql = sql.replace(tableName, vaule);
            }
        }
        MySqlStatementParser mySqlStatementParser = new MySqlStatementParser(sql);
        SQLStatement statement = mySqlStatementParser.parseStatement();
        BoundSql bs = new BoundSql(ms.getConfiguration(), statement.toString(), boundSql.getParameterMappings(), parameterObject);
        MappedStatement newMs = copyFromMappedStatement(ms, new BoundSqlSqlSource(bs));
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                bs.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        args[0] = newMs;

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        tableSet = map.keySet();
        this.map = map;
    }

    public boolean isOpenInterceptor() {
        return openInterceptor;
    }

    public void setOpenInterceptor(boolean openInterceptor) {
        RerouteToTableInterceptor.openInterceptor = openInterceptor;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 通过Druid 获取到SQL的 Visitor
     *
     * @param sqlStr sql
     * @return
     */
    private MySqlSchemaStatVisitor getMySqlSchemaStatVisitor(String sqlStr) {
        MySqlStatementParser parser = new MySqlStatementParser(sqlStr);
        SQLStatement sqlStatement = parser.parseStatementList().get(0);
        MySqlSchemaStatVisitor visitor = new MySqlSchemaStatVisitor();
        sqlStatement.accept(visitor);
        return visitor;
    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
