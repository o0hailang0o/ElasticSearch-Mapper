package com.demo.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * @author liujian on 2019/3/6.
 *
 * 这个注解只能用在入参  基本的排序操作
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sort {

    String field() default "";

    String order() default "asc";

}
