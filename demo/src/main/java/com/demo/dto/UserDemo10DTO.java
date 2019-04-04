package com.demo.dto;

import com.demo.elasticsearch.annotations.Condition;

/**
 * @author liujian on 2019/4/4.
 */
public class UserDemo10DTO {

    @Condition(value="like")
    private String name;

    private Integer sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserDemo10DTO{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
