package com.demo.elasticsearch.methods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.start.EsUtils;

/**
 * @author liujian on 2019/2/11.
 */
public class FindOne<T> implements BaseMethod<T> {

    @Override
    public Object handlerRest(Class<T> modelClass, EsUtils esUtils, Object[] args) {
        String index = ClassHandler.getIndex(modelClass)+"-v"+esUtils.getEsVersion();
        String type = ClassHandler.getType(modelClass);
        String url =  "/"+index+"/"+type+"/"+args[0];
        JSONObject result = esUtils.get(url);
        JSONObject source= result.getJSONObject("_source");
        String idString = ClassHandler.getIdFieldName(modelClass);
        source.put(idString,args[0]);
        return JSON.parseObject(JSON.toJSONString(source),modelClass);
    }
}
