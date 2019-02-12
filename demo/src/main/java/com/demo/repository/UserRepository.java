package com.demo.repository;

import com.demo.dto.UserDemo1DTO;
import com.demo.dto.UserDemo2DTO;
import com.demo.dto.UserDemo3DTO;
import com.demo.elasticsearch.annotations.EsMapper;
import com.demo.elasticsearch.repository.ElasticSearchRepository;
import com.demo.model.User;

import java.util.List;

/**
 * @author liujian on 2019/1/3.
 */
@EsMapper
public interface UserRepository extends ElasticSearchRepository<User>{

    /**
     * 根据name 查询用户信息
     * @return
     */
    List<User> ageGt22(UserDemo1DTO userDemo1DTO);

    /**
     * 分别列出性别的数量
     * @return
     */
    List<UserDemo2DTO> countGroupBySex();

    /**
     * 计算每个性别的年龄
     * @return
     */
    List<UserDemo3DTO> avgAgeGroupBySex();
}
