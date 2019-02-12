package com.demo.elasticsearch.model;

/**
 * @author liujian on 2019/1/28.
 */
public class GroupModel {

    private String fieldName;

    private Integer order;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
