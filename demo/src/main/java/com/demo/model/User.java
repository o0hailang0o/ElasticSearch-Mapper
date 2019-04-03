package com.demo.model;

import com.demo.elasticsearch.annotations.Document;
import com.demo.elasticsearch.annotations.Id;

/**
 * @author liujian on 2018/12/29.
 */
@Document(index = "test",type = "user")
public class User {

    @Id
    private Long userId;

    private String name;

    private Integer age;

    private Integer sex;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
