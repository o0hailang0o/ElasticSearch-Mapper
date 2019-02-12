package com.demo.elasticsearch.proxy;

import java.lang.reflect.Proxy;

/**
 * <p>todo</p>
 *
 * @author zhaoyong
 * @version $Id: ElasticRepositoryProxyFactory , v 0.1  K555 Exp $
 * @date 2019年01月30 16:21
 */
public class ElasticRepositoryProxyFactory<T> {

    private final Class<T> elasticRepositoryInterface;

    public ElasticRepositoryProxyFactory(Class<T> elasticRepositoryInterface) {
        this.elasticRepositoryInterface = elasticRepositoryInterface;
    }

    public Class<T> getElasticRepositoryInterface() {
        return elasticRepositoryInterface;
    }

    protected T newInstance(ElasticRepositoryProxy elasticRepositoryProxy) {
        return (T) Proxy.newProxyInstance(elasticRepositoryInterface.getClassLoader(), new Class[] { elasticRepositoryInterface }, elasticRepositoryProxy);
    }

}
