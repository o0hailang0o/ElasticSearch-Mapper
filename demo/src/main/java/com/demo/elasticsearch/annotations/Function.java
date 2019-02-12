package com.demo.elasticsearch.annotations;

import com.demo.elasticsearch.enums.Fun;

import java.lang.annotation.*;

/**
 * @author liujian on 2019/1/28.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {


    String field() default "";

    Fun value();

}
