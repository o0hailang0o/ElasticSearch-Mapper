package com.demo.elasticsearch.methods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.start.EsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujian on 2019/2/11.
 */
public class FindAll<T> implements BaseMethod<T>{

    @Override
    public Object handlerRest(Class<T> modelClass, EsUtils esUtils,Object[] args) {
        String url = ClassHandler.getRestUrl(modelClass)+"_search";
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
            String idString = ClassHandler.getIdFieldName(modelClass);
            source.put(idString,jsonObject.getString("_id"));
            list.add(JSON.parseObject(JSON.toJSONString(source),modelClass));
        }
        return list;
    }
}
