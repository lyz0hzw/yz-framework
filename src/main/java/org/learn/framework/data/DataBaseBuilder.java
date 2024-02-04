package org.learn.framework.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.ibatis.session.SqlSession;
import org.learn.framework.context.ContextContainer;
import org.learn.framework.context.Environment;
import org.learn.framework.data.dbutils.DbUtilsBuilder;
import org.learn.framework.data.mybatis.MybatisBuilder;

/**
 * @author hongda.li 2022-04-02 19:07
 */
public class DataBaseBuilder {

    public static DataBaseSession buildSession(){
        DataBaseSession session = Environment.getBean(DataBaseSession.class);
        if (session != null){
            return session;
        }
        DataBaseSession newSession = new DataBaseSession();
        newSession.init();
        ContextContainer.getApplicationContext().setBean(newSession);
        return newSession;
    }

    public static SqlSession buildMybatisSession(){
        return MybatisBuilder.buildSession();
    }

    public static SqlSession buildMybatisSession(boolean autoCommit){
        return MybatisBuilder.buildSession(autoCommit);
    }

    public static QueryRunner buildDbUtilsSession(){
        return DbUtilsBuilder.buildSession();
    }
}
