package com.demo.elasticsearch.model;

/**
 * @author liujian on 2019/3/25.
 */
public class GroupBy {

    private String field;

    private Integer order;

    private String key;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "GroupBy{" +
                "field='" + field + '\'' +
                ", order='" + order + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
