package com.demo.dto;

import com.demo.elasticsearch.annotations.Group;
import com.demo.elasticsearch.annotations.Result;

/**
 * @author liujian on 2019/1/31.
 */
public class UserDemo2DTO {

    @Group
    private Integer sex;

    @Result("groupSexCount")
    private Integer count;


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "UserDemo2DTO{" +
                "sex=" + sex +
                ", count=" + count +
                '}';
    }
}
