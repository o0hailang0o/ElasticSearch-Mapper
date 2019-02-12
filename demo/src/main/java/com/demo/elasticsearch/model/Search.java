package com.demo.elasticsearch.model;

import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.start.EsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liujian on 2019/1/11.
 */
@Component
public class Search {

    @Autowired
    private EsUtils esUtils;

    public PageResult search(Model model){
        ConditionString conditionString = new ConditionString(model);
        String json= conditionString.getDslQuery();
        String index = model.getIndex();
        String type = model.getType();
        String url = "/"+index+"/"+type+"/_search";
        JSONObject result = esUtils.get(url,json);
        ResultHandler resultHandler = new ResultHandler(model);
        return resultHandler.getResult(result);
    }

}
