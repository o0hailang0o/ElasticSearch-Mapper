package com.demo.elasticsearch.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.annotations.Function;
import com.demo.elasticsearch.annotations.Group;
import com.demo.elasticsearch.enums.Fun;
import com.demo.elasticsearch.model.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * @author liujian on 2019/1/25.
 */
public class HandlerModel {

    public static void createModel(Model model,Object[] objs){
        if(objs!=null){
            for(Object obj : objs){
                Class clazz = obj.getClass();
                Set<Condition> conditions = getConditions(clazz);
                model.setConditions(conditions);
                Map<String,String> params = getParams(obj);
                model.setParams(params);
            }
        }
    }

    private static Map<String, String> getParams(Object obj) {
        Map<String, String> map = new HashMap<>();
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            map.put(entry.getKey(),entry.getValue()+"");
        }
        return map;
    }

    private static String getType(Class clazz){
        return ClassHandler.getType(clazz);
    }

    private static String getIndex(Class clazz){
        return ClassHandler.getIndex(clazz);
    }

    /**
     * 默认value是= field 默认是 fieldName
     * @param clazz
     * @return
     */
    private static Set<Condition> getConditions(Class clazz){
        Set<Condition> conditions = new HashSet<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            Condition condition = new Condition();
            if(field.isAnnotationPresent(com.demo.elasticsearch.annotations.Condition.class)){
                com.demo.elasticsearch.annotations.Condition conditionAnnotation = field.getAnnotation(com.demo.elasticsearch.annotations.Condition.class);
                String fieldName = conditionAnnotation.field();
                if(StringUtils.isEmpty(fieldName)){
                    condition.setField(field.getName());
                }else {
                    condition.setField(fieldName);
                }
                String value = conditionAnnotation.value();
                if(StringUtils.isEmpty(value)){
                    condition.setValue("=");
                }else {
                    condition.setValue(value);
                }
            }else{
                condition.setField(field.getName());
                condition.setValue("=");
            }
            conditions.add(condition);
        }
        return conditions;
    }

    public static AggsGroup getAggsGroup(Class clazz){
        AggsGroup aggsGroup = new AggsGroup();
        List<String> groups  = new ArrayList<>();
        List<GroupModel> groupModelList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(Group.class)){
                Group group = field.getAnnotation(Group.class);
                GroupModel groupModel = new GroupModel();
                groupModel.setFieldName(field.getName());
                groupModel.setOrder(group.order());
                groupModelList.add(groupModel);
            }
        }
        Collections.sort(groupModelList, new Comparator<GroupModel>() {
            @Override
            public int compare(GroupModel o1, GroupModel o2) {
                return o1.getOrder()-o2.getOrder();
            }
        });
        for(GroupModel groupModel : groupModelList){
            groups.add(groupModel.getFieldName());
        }
        aggsGroup.setGroups(groups);
        List<Condition> conditions = getFun(clazz);
        aggsGroup.setValues(conditions);
        return aggsGroup;
    }

    private static List<Condition> getFun(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        List<Condition> conditions = new ArrayList<>();
        for (Field field : fields){
            if(field.isAnnotationPresent(Function.class)){
                Function function = field.getAnnotation(Function.class);
                String fieldName = field.getName();
                if(!StringUtils.isEmpty(function.field())){
                    fieldName = function.field();
                }
                Fun fun = function.value();
                Condition condition = new Condition();
                condition.setField(fieldName);
                condition.setValue(fun.getName());
                conditions.add(condition);
            }
        }
        return conditions;
    }


}
