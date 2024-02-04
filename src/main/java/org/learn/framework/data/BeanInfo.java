package org.learn.framework.data;

import cn.hutool.core.util.ReflectUtil;
import org.learn.framework.annotation.Column;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 对象信息构造
 */
public class BeanInfo {

    private final Class<?> beanClass;

    /**
     * 实体对象属性
     */
    private final List<Field> fields;
    /**
     * 数据库字段名
     */
    private final List<String> columnNames;

    private final List<Class<?>> fieldTypes;

    public BeanInfo(Class<?> beanClass){
        this.beanClass = beanClass;
        this.fields = new ArrayList<>();
        this.columnNames = new ArrayList<>();
        this.fieldTypes = new ArrayList<>();
        initialize();
    }

    private void initialize(){
        // 通过反射获取bean的属性
        Arrays.asList(ReflectUtil.getFields(beanClass)).forEach(
                field -> {
                    fields.add(field);
                    fieldTypes.add(field.getType());
                    Column column = field.getAnnotation(Column.class); // 变量名和数据库字段名不一致
                    if (column == null || "".equals(column.value())) {
                        columnNames.add(field.getName()); // 变量名和数据库字段名一致的情况
                    }else {
                        columnNames.add(column.value()); // 变量名和数据库字段名不一致的情况
                    }
                }
        );
    }

    public List<Class<?>> getFieldTypes() {
        return fieldTypes;
    }

    public List<Field> getFieldNames() {
        return fields;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
