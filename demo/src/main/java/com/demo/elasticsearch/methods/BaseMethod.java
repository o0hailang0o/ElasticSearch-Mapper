package com.demo.elasticsearch.methods;

import com.demo.elasticsearch.start.EsUtils;

/**
 * @author liujian on 2019/2/11.
 */
public interface BaseMethod<T> {

    /**
     * 处理es的rest语句
     * @param modelClass
     * @param esUtils
     * @return
     */
     Object handlerRest(Class<T> modelClass,EsUtils esUtils,Object[] args);

}
