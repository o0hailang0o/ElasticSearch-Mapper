package com.demo.elasticsearch.model;

import java.util.Objects;

/**
 * @author liujian on 2019/1/5.
 */
public class Condition {

    private String field;

    private String value;

    private String mapping;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Condition condition = (Condition) o;
        return Objects.equals(field, condition.field) && Objects.equals(value, condition.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, value);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", mapping='" + mapping + '\'' +
                '}';
    }
}
