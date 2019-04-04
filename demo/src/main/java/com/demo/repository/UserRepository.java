package com.demo.repository;

import com.demo.dto.*;
import com.demo.elasticsearch.annotations.EsMapper;
import com.demo.elasticsearch.annotations.Limit;
import com.demo.elasticsearch.annotations.Page;
import com.demo.elasticsearch.model.PageData;
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

    /**
     * 计算出平均年龄以及年龄总和
     * @return
     */
    UserDemo8DTO avgAndTotalAge();

    /**
     * 查询年龄大于20 小于30的用户
     * @param userDemo9DTO
     * @return
     */
    List<User> findUsersByAgeRegion(UserDemo9DTO userDemo9DTO);

    /**
     * 根据姓名 性别搜索
     * @param userDemo10DTO
     * @param page
     * @param limit
     * @return
     */
    PageData<User> searchUser(UserDemo10DTO userDemo10DTO, @Page int page, @Limit int limit);
}
