package com.demo.elasticsearch.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author liujian on 2018/12/28.
 */
public interface ElasticSearchBaseRepository<T>{

    /**
     * 查询所有索引下的type的documents
     *
     * findAll方法，限定size=10000，设置最大值是无效的
     *
     * 注意的是 findAll方法并不能查询所有的数据
     *
     * @return
     */
    List<T> findAll();

    /**
     * 保存方法，
     * PUT index/type
     * {
     *     t.json
     * }
     *
     * 如果id存在 就是update 重新覆盖整个document
     *
     * 如果id不存在 就是insert 重新添加type的内容
     * @param t
     * @return
     */
    T save(T t);

    /**
     * 根据主键ID 查询出对应的document
     * @param id
     * @return
     */
    T findOne(Serializable id);

    /**
     * 批量save操作
     * @param list
     */
    void bulkSave(List<T> list);
}
