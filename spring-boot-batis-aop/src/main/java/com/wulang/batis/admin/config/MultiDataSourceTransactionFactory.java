package com.wulang.batis.admin.config;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import javax.sql.DataSource;

/**
 * @author wulang
 * @create 2019/12/10/17:48
 */
public class MultiDataSourceTransactionFactory extends SpringManagedTransactionFactory {
    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new MultiDataSourceTransaction(dataSource);
    }
}
