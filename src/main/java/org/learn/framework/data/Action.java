package org.learn.framework.data;

import java.util.Arrays;
import java.util.Objects;

/**
 * sql执行动作
 */
public class Action {

    private String sql;

    private Object[] params;

    public String getSql() {
        return sql;
    }

    public Action setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public Action setParams(Object[] params) {
        this.params = params;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Action action = (Action) o;
        return Objects.equals(sql, action.sql) && Arrays.equals(params, action.params);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sql);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }
}
