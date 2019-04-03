package com.demo.elasticsearch.annotations;

/**
 * @author liujian on 2019/3/23.
 */

import java.lang.annotation.*;

/**
 * 分页 page limit  es当中 相当于from = （page-1）*limit  size = limit
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Page {
}
