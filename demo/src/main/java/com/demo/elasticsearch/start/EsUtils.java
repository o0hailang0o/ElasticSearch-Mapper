package com.demo.elasticsearch.start;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 *@author liujian create 2019/1/3  ElasticSearch 的调用rest的方法
 */
@Component
public class EsUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestClient restClient;

    private JSONObject rest(String url, String json, String type) {
        try {
            if(json == null || json.trim().equals("")){
                json="{}";
            }
            HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);
            logger.debug("\n"+type.toUpperCase()+"  "+url+"\n"+json);
            Response response = restClient.performRequest(type, url, Collections.<String, String>emptyMap(), entity);
            InputStream content = response.getEntity().getContent();
            String result = IOUtils.toString(content, "UTF-8");
            return JSONObject.parseObject(result);
        }catch (IOException e){
            logger.error("\n"+type.toUpperCase()+"  "+url+"\n"+json);
            throw new RuntimeException(e.getMessage());
        }
    }

    public JSONObject put(String url, String json){
        return rest(url,json,"put");
    }

    public JSONObject put(String url){
        return rest(url,null,"put");
    }

    public JSONObject get(String url, String json){
        return rest(url,json,"get");
    }

    public JSONObject get(String url){
        return rest(url,null,"get");
    }

    public JSONObject post(String url, String json){
        return rest(url,json,"post");
    }

    public JSONObject post(String url){
        return rest(url,null,"post");
    }

    public JSONObject delete(String url, String json){
        return rest(url,json,"delete");
    }

    public JSONObject delete(String url){
        return rest(url,null,"delete");
    }

}
