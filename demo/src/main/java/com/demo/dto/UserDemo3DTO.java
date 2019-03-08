package com.demo.dto;

import com.demo.elasticsearch.annotations.Function;
import com.demo.elasticsearch.annotations.Group;
import com.demo.elasticsearch.enums.Fun;

import java.math.BigDecimal;

/**
 * @author liujian on 2019/2/1.
 */
public class UserDemo3DTO {

    @Group
    private Integer sex;

    @Function(field = "age",value = Fun.AVG,order = "asc")
    private BigDecimal avgAge;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public BigDecimal getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(BigDecimal avgAge) {
        this.avgAge = avgAge;
    }

    @Override
    public String toString() {
        return "UserDemo3DTO{" +
                "sex=" + sex +
                ", avgAge=" + avgAge +
                '}';
    }
}
