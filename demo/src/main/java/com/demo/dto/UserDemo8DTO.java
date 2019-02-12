package com.demo.dto;

import com.demo.elasticsearch.annotations.Function;
import com.demo.elasticsearch.enums.Fun;

import java.math.BigDecimal;

/**
 * @author liujian on 2019/2/12.
 */
public class UserDemo8DTO {

    @Function(field = "age",value = Fun.SUM)
    private BigDecimal totalAge;

    @Function(field = "age",value = Fun.AVG)
    private BigDecimal avgAge;

    public BigDecimal getTotalAge() {
        return totalAge;
    }

    public void setTotalAge(BigDecimal totalAge) {
        this.totalAge = totalAge;
    }

    public BigDecimal getAvgAge() {
        return avgAge;
    }

    public void setAvgAge(BigDecimal avgAge) {
        this.avgAge = avgAge;
    }

    @Override
    public String toString() {
        return "UserDemo8DTO{" +
                "totalAge=" + totalAge +
                ", avgAge=" + avgAge +
                '}';
    }
}
