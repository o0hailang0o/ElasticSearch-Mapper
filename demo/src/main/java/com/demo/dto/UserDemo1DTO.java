package com.demo.dto;

import com.demo.elasticsearch.annotations.Condition;
import com.demo.elasticsearch.annotations.Document;

/**
 * @author liujian on 2019/1/30.
 */
public class UserDemo1DTO {

    @Condition(value=">")
    private Integer age;

    private Integer sex;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
