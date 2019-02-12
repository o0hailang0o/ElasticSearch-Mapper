package com.demo.elasticsearch.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.annotations.Document;
import com.demo.elasticsearch.annotations.Id;
import com.demo.elasticsearch.exceptions.NotFoundIdException;

import java.lang.reflect.Field;

/**
 * @author liujian on 2018/12/28.
 */
public class ClassHandler{

    /**
     * 获得该类的主键名称
     */
    public static String getIdFieldName(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent((Id.class))){
                return field.getName();
            }
        }
        throw new NotFoundIdException("not found primary,class must add Id annotation");
    }

    /**
     * 获取类的index名称和type名称
     */
    public static String getRestUrl(Class clazz){
        String index = getIndex(clazz);
        String type = getType(clazz);
        return "/"+index+"/"+type+"/";
    }

    public static String getIndex(Class clazz){
        if(clazz.isAnnotationPresent(Document.class)) {
            Document document = (Document) clazz.getAnnotation(Document.class);
            return document.index();
        }else {
            return  clazz.getName();
        }
    }

    public static String getType(Class clazz){
        if(clazz.isAnnotationPresent(Document.class)) {
            Document document = (Document) clazz.getAnnotation(Document.class);
            return document.type();
        }else {
            return clazz.getName();
        }
    }

    public static Object getIdValue(Object obj){
        Class clazz = obj.getClass();
        String idString = getIdFieldName(clazz);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
        return jsonObject.get(idString);
    }
}
