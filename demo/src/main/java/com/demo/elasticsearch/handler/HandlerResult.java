package com.demo.elasticsearch.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.annotations.Function;
import com.demo.elasticsearch.annotations.Group;
import com.demo.elasticsearch.annotations.Id;
import com.demo.elasticsearch.annotations.Result;
import com.demo.elasticsearch.model.BeanHump;
import com.demo.elasticsearch.model.PageData;
import com.demo.elasticsearch.model.PageResult;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liujian on 2019/1/30.
 */
public class HandlerResult {

    //映射DTO  跟 group注解 field都有关系 如果映射结果 可以@Result
    public static <T> List<T> resultMap(PageResult pageResult, Class<T> clazz) {
        List<JSONObject> jsonObjects = pageResult.getJsonObjectList();
        if(!CollectionUtils.isEmpty(jsonObjects)){
            for(JSONObject jsonObject :jsonObjects ){
                handlerJson(jsonObject,clazz);
            }
            return JSONArray.parseArray(JSON.toJSONString(jsonObjects),clazz);
        }
        return new ArrayList<>();
    }

    public static <T> PageData<T> resultPageMap(PageResult pageResult, Class<T> clazz) {
        PageData<T> pageData = new PageData<>();
        List<T> resultList = resultMap(pageResult, clazz);
        pageData.setDataList(resultList);
        pageData.setCount(pageResult.getCount());
        return pageData;
    }

    private static <T> void handlerJson(JSONObject jsonObject, Class<T> clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Id.class)){
                handlerId(field,jsonObject);
            } else if(field.isAnnotationPresent(Group.class)){
                handlerGroup(field,jsonObject);
            }else if(field.isAnnotationPresent(Function.class)){
                handlerFunction(field,jsonObject);
            } else if(field.isAnnotationPresent(Result.class)){
                handlerResult(field,jsonObject);
            }
        }
    }

    private static void handlerId(Field field, JSONObject jsonObject){
        jsonObject.put(field.getName(),jsonObject.get("_id"));
        jsonObject.remove("_id");
    }

    private static void handlerGroup(Field field, JSONObject jsonObject){
        Group group = field.getAnnotation(Group.class);
        String fieldName = field.getName();
        if(!StringUtils.isEmpty(group.field())){
            fieldName = group.field();
        }
        String key = "group_"+fieldName+"_value";
        jsonObject.put(field.getName(),jsonObject.get(BeanHump.underlineToCamel2(key)));
        jsonObject.remove(BeanHump.underlineToCamel2(key));
    }

    private static void handlerFunction(Field field, JSONObject jsonObject){
        Function function = field.getAnnotation(Function.class);
        String fieldName = field.getName();
        if(!StringUtils.isEmpty(function.field())){
            fieldName = function.field();
        }
        String key = function.value()+"_"+fieldName;
        jsonObject.put(field.getName(),jsonObject.get(BeanHump.underlineToCamel2(key)));
        jsonObject.remove(BeanHump.underlineToCamel2(BeanHump.underlineToCamel2(key)));
    }

    private static void handlerResult(Field field, JSONObject jsonObject){
        Result result = field.getAnnotation(Result.class);
        jsonObject.put(field.getName(),jsonObject.get(result.value()));
        jsonObject.remove(result.value());
    }
}
