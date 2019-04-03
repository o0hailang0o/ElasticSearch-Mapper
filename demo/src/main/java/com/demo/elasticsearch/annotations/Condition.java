package com.demo.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * @author liujian on 2019/1/25.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {

    String field() default "";

    String value() default "=";

    String mapping() default "";
}
