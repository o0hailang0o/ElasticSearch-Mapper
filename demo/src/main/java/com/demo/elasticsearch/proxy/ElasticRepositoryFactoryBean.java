package com.demo.elasticsearch.proxy;

import com.demo.elasticsearch.model.Search;
import org.springframework.beans.factory.FactoryBean;

/**
 *
 * @author zhaoyong
 * @version $Id: ElasticRepositoryFactoryBean , v 0.1  K555 Exp $
 * @date 2019年01月30 16:35
 */
public class ElasticRepositoryFactoryBean<T> implements FactoryBean<T> {

    private Class<T> elasticRepositoryInterface;

    private Search search;

    public ElasticRepositoryFactoryBean() {
    }

    public ElasticRepositoryFactoryBean(Class<T> elasticRepositoryInterface) {
        this.elasticRepositoryInterface = elasticRepositoryInterface;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public T getObject() throws Exception {
        ElasticRepositoryProxyFactory<T> proxyFactory = new ElasticRepositoryProxyFactory(elasticRepositoryInterface);
        ElasticRepositoryProxy proxy = new ElasticRepositoryProxy(search, elasticRepositoryInterface);
        return proxyFactory.newInstance(proxy);
    }

    @Override
    public Class<T> getObjectType() {
        return elasticRepositoryInterface;
    }


    @Override
    public boolean isSingleton() {
        return true;
    }

}
