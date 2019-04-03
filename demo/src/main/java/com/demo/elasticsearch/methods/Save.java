package com.demo.elasticsearch.methods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.exceptions.NotFoundIdException;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.start.EsUtils;
import org.springframework.util.StringUtils;

/**
 * @author liujian on 2019/2/11.
 */
public class Save<T> implements BaseMethod<T> {

    @Override
    public Object handlerRest(Class<T> modelClass, EsUtils esUtils, Object[] args) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(args[0]));
        String idString = ClassHandler.getIdFieldName(modelClass);
        String idValue = jsonObject.get(idString).toString();
        if(StringUtils.isEmpty(idValue)){
            throw new NotFoundIdException("not found primary,class must add Id annotation");
        }
        String index = ClassHandler.getIndex(modelClass)+"-v"+esUtils.getEsVersion();
        String type = ClassHandler.getType(modelClass);
        String url =  "/"+index+"/"+type+"/"+idValue;
        jsonObject.remove(idString);
        String json = jsonObject.toJSONString();
        esUtils.put(url,json);
        return args[0];
    }
}
