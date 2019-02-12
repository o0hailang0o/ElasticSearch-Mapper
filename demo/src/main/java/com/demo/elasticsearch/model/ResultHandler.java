package com.demo.elasticsearch.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liujian on 2019/1/5.
 */
public class ResultHandler {

    private Model model;

    /**
     * 处理结果 这样的 要区分是否聚合
     *
     * 如果 不是聚合 就是要取query元素的内容
     *
     *
     */
    public ResultHandler(Model model){
        this.model = model;
    }

    public PageResult getResult(JSONObject result){
        if(model!=null){
            if(model.getAggs()==null||(CollectionUtils.isEmpty(model.getAggs().getGroups())&& CollectionUtils.isEmpty(model.getAggs().getValues()))){
                return getNoAggResult(result);
            }else{
                return getAggResult(result);
            }
        }else {
            return new PageResult();
        }
    }

    private PageResult getNoAggResult(JSONObject result) {
        PageResult pageResult = new PageResult();
        List<JSONObject> jsonObjects =  new ArrayList<>();
        Long count = result.getJSONObject("hits").getLong("total");
        JSONArray hits = result.getJSONObject("hits").getJSONArray("hits");
        for(int i=0;i<hits.size();i++){
            JSONObject jsonObject = (JSONObject)hits.get(i);
            String id = jsonObject.getString("_id");
            JSONObject json = jsonObject.getJSONObject("_source");
            json.put("_id",id);
            jsonObjects.add(json);
        }
        pageResult.setCount(count);
        pageResult.setJsonObjectList(jsonObjects);
        return pageResult;
    }


    private PageResult getAggResult(JSONObject result) {
        PageResult pageResult = new PageResult();
        List<String> groups = model.getAggs().getGroups();
        int size = 0;
        if(groups != null){
            size = groups.size();
        }
        List<JSONObject> jsonObjects = new ArrayList<>();
        switch (size){
            case 0:
                jsonObjects = analyzeAnalyze0(result);
                break;
            case 1:
                jsonObjects = analyzeAnalyze1(result);
                break;
            case 2:
                jsonObjects = analyzeAnalyze2(result);
                break;
            case 3:
                jsonObjects = analyzeAnalyze3(result);
                break;
            default:
              throw new RuntimeException("too many groups");
        }
        Long count = Long.parseLong(jsonObjects.size()+"");
        if(model!=null){
            Map<String, String> params = model.getParams();
            if(params == null){
                params = new HashMap<>(16);
            }
            Integer limit = StringUtils.isEmpty(params.get("limit"))?10000:Integer.parseInt(params.get("limit"));
            Integer page =  StringUtils.isEmpty(params.get("page"))?1:Integer.parseInt(params.get("page"));
            int from = (page-1)*limit;
            size = (page-1)*limit+limit;
            List<JSONObject> jsonObjects1 = new ArrayList<>();
            for(int i=from;i<size&&i<jsonObjects.size();i++){
                jsonObjects1.add(jsonObjects.get(i));
            }
            pageResult.setCount(count);
            pageResult.setJsonObjectList(jsonObjects1);
            return pageResult;
        }
        return pageResult;
    }


    private List<JSONObject> analyzeAnalyze0(JSONObject result){
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<Condition> conditions = model.getAggs().getValues();
        JSONObject aggregations = result.getJSONObject("aggregations");
        if(conditions!=null){
            JSONObject jsonObject = new JSONObject();
            for(Condition condition : conditions){
                String field  = condition.getField();
                String value = condition.getValue();
                jsonObject.put(BeanHump.underlineToCamel2(value+"_"+field),aggregations.getJSONObject(value+"_"+field).getString("value"));
            }
            jsonObjects.add(jsonObject);
        }
        return jsonObjects;
    }

    private List<JSONObject> analyzeAnalyze1(JSONObject result){
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<Condition> conditions = model.getAggs().getValues();
        List<String> groups = model.getAggs().getGroups();
        JSONObject aggregations = result.getJSONObject("aggregations");
        JSONArray buckets = aggregations.getJSONObject("group_"+groups.get(0)).getJSONArray("buckets");
        for(int i=0;i<buckets.size();i++){
            JSONObject bucket = (JSONObject)buckets.get(i);
            if(conditions!=null){
                JSONObject jsonObject = new JSONObject();
                for(Condition condition : conditions){
                    String field  = condition.getField();
                    String value = condition.getValue();
                    jsonObject.put(BeanHump.underlineToCamel2(value+"_"+field),bucket.getJSONObject(value+"_"+field).getString("value"));
                }
                jsonObject.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_value"),bucket.getString("key"));
                jsonObject.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_count"),bucket.getString("doc_count"));
                jsonObjects.add(jsonObject);
            }
        }
        return jsonObjects;
    }

    private List<JSONObject> analyzeAnalyze2(JSONObject result){
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<Condition> conditions = model.getAggs().getValues();
        List<String> groups = model.getAggs().getGroups();
        JSONObject aggregations = result.getJSONObject("aggregations");
        JSONArray buckets1 = aggregations.getJSONObject("group_"+groups.get(0)).getJSONArray("buckets");
        for(int i=0;i<buckets1.size();i++){
            JSONObject bucket1 = (JSONObject)buckets1.get(i);
            String key1 = bucket1.getString("key");
            String value1 = bucket1.getString("doc_count");
            JSONArray buckets2 = bucket1.getJSONObject("group_"+groups.get(1)).getJSONArray("buckets");
            for(int j=0;j<buckets2.size();j++){
                JSONObject bucket2 = (JSONObject)buckets2.get(j);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_value"),bucket2.getString("key"));
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_count"),bucket2.getString("doc_count"));
                if(conditions!=null){
                    for(Condition condition : conditions){
                        String field  = condition.getField();
                        String value = condition.getValue();
                        jsonObject2.put(BeanHump.underlineToCamel2(value+"_"+field),bucket2.getJSONObject(value+"_"+field).getString("value"));
                    }
                }
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_value"),key1);
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_count"),value1);
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_value"),bucket2.getString("key"));
                jsonObject2.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_count"),bucket2.getString("doc_count"));
                jsonObjects.add(jsonObject2);
            }

        }
        return jsonObjects;
    }

    private List<JSONObject> analyzeAnalyze3(JSONObject result){
        List<JSONObject> jsonObjects = new ArrayList<>();
        List<Condition> conditions = model.getAggs().getValues();
        List<String> groups = model.getAggs().getGroups();
        JSONObject aggregations = result.getJSONObject("aggregations");
        JSONArray buckets1 = aggregations.getJSONObject("group_"+groups.get(0)).getJSONArray("buckets");
        for(int i=0;i<buckets1.size();i++){
            JSONObject bucket1 = (JSONObject)buckets1.get(i);
            String key1 = bucket1.getString("key");
            String value1 = bucket1.getString("doc_count");
            JSONArray buckets2 = bucket1.getJSONObject("group_"+groups.get(1)).getJSONArray("buckets");
            for(int j=0;j<buckets2.size();j++){
                JSONObject bucket2 = (JSONObject)buckets2.get(j);
                String key2 = bucket2.getString("key");
                String value2 = bucket2.getString("doc_count");
                JSONArray buckets3 = bucket2.getJSONObject("group_"+groups.get(2)).getJSONArray("buckets");
                for(int x=0;x<buckets3.size();x++){
                    JSONObject jsonObject3 = new JSONObject();
                    JSONObject bucket3 = (JSONObject)buckets3.get(x);
                    if(conditions!=null){
                        for(Condition condition : conditions){
                            String field  = condition.getField();
                            String value = condition.getValue();
                            jsonObject3.put(BeanHump.underlineToCamel2(value+"_"+field),bucket3.getJSONObject(value+"_"+field).getString("value"));
                        }
                     }
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_value"),key1);
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(0)+"_count"),value1);
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_value"),key2);
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(1)+"_count"),value2);
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(2)+"_value"),bucket3.getString("key"));
                    jsonObject3.put(BeanHump.underlineToCamel2("group_"+groups.get(2)+"_count"),bucket3.getString("doc_count"));
                    jsonObjects.add(jsonObject3);
                }
            }

        }
        return jsonObjects;
    }
}
