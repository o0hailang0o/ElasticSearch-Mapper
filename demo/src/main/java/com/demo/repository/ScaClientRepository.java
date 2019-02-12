package com.demo.repository;

import com.demo.elasticsearch.annotations.EsMapper;
import com.demo.elasticsearch.repository.ElasticSearchRepository;
import com.demo.model.ScaClient;

/**
 * @author liujian on 2018/12/28.
 */
@EsMapper
public interface ScaClientRepository extends ElasticSearchRepository<ScaClient> {

}
