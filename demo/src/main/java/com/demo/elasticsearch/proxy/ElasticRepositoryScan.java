package com.demo.elasticsearch.proxy;

import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>todo</p>
 *
 * @author zhaoyong
 * @version $Id: ElasticRepositoryScan , v 0.1  K555 Exp $
 * @date 2019年01月30 17:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(ElasticRepositoryScannerRegistrar.class)
public @interface ElasticRepositoryScan {

    String[] value() default {};

    String searchRef();

    String[] basePackages() default {};

    Class<?>[] basePackageClasses() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;

    Class<? extends ElasticRepositoryFactoryBean> factoryBean() default ElasticRepositoryFactoryBean.class;

}
