package com.demo.elasticsearch.methods;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.start.EsUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liujian on 2019/2/11.
 */
public class BulkSave<T> implements BaseMethod<T> {

    @Override
    public Object handlerRest(Class<T> modelClass, EsUtils esUtils, Object[] args) {
        String index = ClassHandler.getIndex(modelClass)+"-v"+esUtils.getEsVersion();
        String type = ClassHandler.getType(modelClass);
        String idString = ClassHandler.getIdFieldName(modelClass);
        String url = "_bulk";
        String json="";
        List<T> list = (List<T>)args[0];
        if(!CollectionUtils.isEmpty(list)){
            for(T t :list){
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(t));
                json+="{\"index\":{\"_index\":\""+index+"\",\"_type\":\""+type+"\",\"_id\":\""+jsonObject.get(idString)+"\"}}\n";
                jsonObject.remove(idString);
                json+=jsonObject.toJSONString()+"\n";
            }
        }
        esUtils.post(url,json);
        return 1;
    }
}
