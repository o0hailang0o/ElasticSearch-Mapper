package com.demo.elasticsearch.annotations;

import java.lang.annotation.*;

/**
 * @author liujian on 2018/12/28.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Document {

    String index() default "";

    String type() default "";

}
