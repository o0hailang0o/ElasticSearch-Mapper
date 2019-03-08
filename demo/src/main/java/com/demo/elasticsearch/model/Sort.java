package com.demo.elasticsearch.model;

/**
 * @author liujian on 2019/2/20.
 */
public class Sort {

    private String field;

    private String order;

    private Integer status;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "field='" + field + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
