package com.demo.dto;

import com.demo.elasticsearch.annotations.Condition;

/**
 * @author liujian on 2019/4/4.
 */
public class UserDemo9DTO {

    @Condition(field = "age",value = ">",mapping = "minAge")
    private Integer minAge;
    @Condition(field = "age",value = "<",mapping = "maxAge")
    private Integer maxAge;

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "UserDemo9DTO{" +
                "minAge=" + minAge +
                ", maxAge=" + maxAge +
                '}';
    }
}
