package org.learn.framework.data.dbutils;

import org.apache.commons.dbutils.QueryRunner;
import org.learn.framework.data.DataBaseSource;

/**
 * @author hongda.li 2022-04-02 19:05
 */
public class DbUtilsBuilder {

    public static QueryRunner buildSession(){
        return new QueryRunner(DataBaseSource.getDataSource());
    }
}
