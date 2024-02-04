package org.learn.framework.data.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.learn.framework.data.DataBaseSource;
import org.learn.framework.data.YzMapper;
import org.learn.framework.util.ClassUtils;

import javax.sql.DataSource;

/**
 * Mybatis构造器
 */
public class MybatisBuilder {

    private static SqlSessionFactory FACTORY;

    private static boolean init = false;

    private MybatisBuilder() {
    }

    public static SqlSession buildSession() {
        if (!init) {
            initSqlSessionFactory();
        }
        return FACTORY.openSession();
    }

    public static SqlSession buildSession(boolean autoCommit){
        if (!init) {
            initSqlSessionFactory();
        }
        return FACTORY.openSession(autoCommit);
    }

    /**
     * 初始化Mybatis的SqlSessionFactory
     */
    private static void initSqlSessionFactory() {
        DataSource dataSource = DataBaseSource.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.setLogImpl(MybatisLog.class);
        TypeAliasRegistry typeAliasRegistry = configuration.getTypeAliasRegistry();
        // 扫描带有YzMapper注解的和Mapper注解的类和接口，注册别名
        ClassUtils.getClassesWithAnnotation(YzMapper.class, false).forEach(mapper -> {
            typeAliasRegistry.registerAlias(mapper.getSimpleName(), mapper);
            configuration.addMapper(mapper);
        });
        ClassUtils.getClassesWithAnnotation(Mapper.class, false).forEach(mapper -> {
            typeAliasRegistry.registerAlias(mapper.getSimpleName(), mapper);
            configuration.addMapper(mapper);
        });
        FACTORY = new SqlSessionFactoryBuilder().build(configuration);
        init = true;
    }
}
