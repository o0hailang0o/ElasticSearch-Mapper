package com.demo.elasticsearch.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.exceptions.NotFoundIdException;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.start.EsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujian on 2018/12/28.
 */
@Repository
public class ElasticSearchBaseRepositoryImpl<T> implements ElasticSearchBaseRepository<T> {

    @Autowired
    private EsUtils esUtils;

    @Override
    public List<T> findAll() {
        Class<T> clazz = getTClass();
        String url = ClassHandler.getRestUrl(clazz)+"_search";
        String json ="{\n" +
                     " \"size\":10000,\n"+
                     "  \"query\": {\n" +
                     "    \"match_all\": {}\n" +
                     "  }\n" +
                     "}";
        JSONObject result = esUtils.get(url,json);
        List<T> list = new ArrayList<>();
        JSONArray hits = result.getJSONObject("hits").getJSONArray("hits");
        for(int i=0;i<hits.size();i++){
            JSONObject jsonObject = (JSONObject)hits.get(i);
            JSONObject source = jsonObject.getJSONObject("_source");
            list.add(JSON.parseObject(JSON.toJSONString(source),clazz));
        }
        return list;
    }

    @Override
    public T save(T t) {
        Class<T> clazz = getTClass();
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(t));
        String idString = ClassHandler.getIdFieldName(clazz);
        String idValue = jsonObject.get(idString).toString();
        if(StringUtils.isEmpty(idValue)){
           throw new NotFoundIdException("not found primary,class must add Id annotation");
        }
        String url = ClassHandler.getRestUrl(clazz)+idValue;
        jsonObject.remove(idString);
        String json = jsonObject.toJSONString();
        esUtils.put(url,json);
        return t;
    }

    @Override
    public T findOne(Serializable id) {
        Class<T> clazz = getTClass();
        String url = ClassHandler.getRestUrl(clazz)+id;
        JSONObject result = esUtils.get(url);
        JSONObject source= result.getJSONObject("_source");
        String idString = ClassHandler.getIdFieldName(clazz);
        source.put(idString,id);
        return JSON.parseObject(JSON.toJSONString(source),clazz);
    }

    @Override
    public void bulkSave(List<T> list) {
        Class<T> clazz = getTClass();
        String index = ClassHandler.getIndex(clazz);
        String type = ClassHandler.getType(clazz);
        String idString = ClassHandler.getIdFieldName(clazz);
        String url = "_bulk";
        String json="";
        if(!CollectionUtils.isEmpty(list)){
            for(T t :list){
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(t));
                json+="{\"index\":{\"_index\":\""+index+"\",\"_type\":\""+type+"\",\"_id\":\""+jsonObject.get(idString)+"\"}}\n";
                jsonObject.remove(idString);
                json+=jsonObject.toJSONString()+"\n";
            }
        }
        esUtils.post(url,json);
    }


    private Class<T> getTClass() {
        //获得泛型的类型
        Type type = getClass().getGenericSuperclass();
        ParameterizedType p=(ParameterizedType)type;
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        return (Class<T>) p.getActualTypeArguments()[0];
    }
}
