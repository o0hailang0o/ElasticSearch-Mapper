package com.demo.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * @author liujian on 2019/1/25.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Group {

    String field() default "";

    int order() default 0;

    String condition() default "";
}
