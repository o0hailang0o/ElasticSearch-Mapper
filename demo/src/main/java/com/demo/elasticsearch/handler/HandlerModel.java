package com.demo.elasticsearch.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.elasticsearch.annotations.Function;
import com.demo.elasticsearch.annotations.Group;
import com.demo.elasticsearch.annotations.Limit;
import com.demo.elasticsearch.annotations.Page;
import com.demo.elasticsearch.enums.Fun;
import com.demo.elasticsearch.model.*;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * @author liujian on 2019/1/25.
 */
public class HandlerModel {

    private HandlerModel(){}

    public static void createModel(Model model,Object[] objs)throws Exception{
        if(objs!=null){
            for(Object obj : objs){
                Class clazz = obj.getClass();
                if(!isBaseType(clazz)){
                    Set<Condition> conditions = getConditions(clazz);
                    model.setConditions(conditions);
                    getAggsGroup(model,obj,0);
                    getFun(model,clazz);
                    Map<String,String> params = getParams(obj);
                    model.setParams(params);
                    Sort sort = getSort(clazz);
                    model.setSort(sort);
                }
            }
        }
    }

    //判断基本类型
    private static Boolean isBaseType(Class clazz){
        return clazz.equals(Integer.class)||clazz.equals(Long.class)||clazz.equals(Short.class)||clazz.equals(String.class)
                ||clazz.equals(Character.class);
    }


    private static Map<String, String> getParams(Object obj) {
        Map<String, String> map = new HashMap<>(16);
    //    Map<String,String> annotationMap = annotationMapping(obj);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            if(!StringUtils.isEmpty(entry.getValue())){
                map.put(entry.getKey(),entry.getValue()+"");
            }
        }
        return map;
    }

    private static Map<String,String> annotationMapping(Object obj){
        Map<String,String> map = new HashMap<>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(com.demo.elasticsearch.annotations.Condition.class)){
                com.demo.elasticsearch.annotations.Condition conditionAnnotation = field.getAnnotation(com.demo.elasticsearch.annotations.Condition.class);
                if(!StringUtils.isEmpty(conditionAnnotation.field())){
                    map.put(field.getName(),conditionAnnotation.field());
                }
                map.put(field.getName(),field.getName());
            }
        }
        return map;
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
                if(!StringUtils.isEmpty(conditionAnnotation.mapping())){
                    condition.setMapping(conditionAnnotation.mapping());
                }
            }else{
                condition.setField(field.getName());
                condition.setValue("=");
            }
            conditions.add(condition);
        }
        return conditions;
    }

    public static void getAggsGroup(Model model,Object obj,int type)throws Exception{
        Class clazz = obj.getClass();
        if(type == 1){
             clazz =  (Class)obj;
        }
        AggsGroup aggsGroup = new AggsGroup();
        List<GroupBy> groups = new ArrayList<>();
        if(model.getAggs()!=null && model.getAggs().getGroups() != null){
            groups  = model.getAggs().getGroups();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(Group.class)){
                Group group = field.getAnnotation(Group.class);
                GroupBy groupBy = new GroupBy();
                groupBy.setField(field.getName());
                groupBy.setOrder(group.order());
                groupBy.setKey(group.key());
                if(isFilterGroupCondition(group.condition(),obj)||type == 1){
                    groups.add(groupBy);
                }
            }
        }
        Collections.sort(groups, new Comparator<GroupBy>() {
            @Override
            public int compare(GroupBy o1, GroupBy o2) {
                return o1.getOrder()-o2.getOrder();
            }
        });
        aggsGroup.setGroups(groups);
        model.setAggs(aggsGroup);

    }

    private static boolean isFilterGroupCondition(String condition,Object obj)throws Exception{
        if(StringUtils.isEmpty(condition)){
            return true;
        }
        ExpressionParser parser=new SpelExpressionParser();
        EvaluationContext ctx = new StandardEvaluationContext();
        //在上下文中设置变量，变量名为user，内容为user对象
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            ctx.setVariable(field.getName(), field.get(obj));
        }
        Boolean value = parser.parseExpression(condition).getValue(ctx,Boolean.class);
        return value;
    }


    public static void getFun(Model model,Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        List<Condition> conditions = new ArrayList<>();
        if(model.getAggs() != null && model.getAggs().getValues() != null){
            conditions = model.getAggs().getValues();
        }
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
                if(model.getAggs()!=null){
                    model.getAggs().setValues(conditions);
                }else{
                    AggsGroup aggsGroup = new AggsGroup();
                    model.setAggs(aggsGroup);
                    model.getAggs().setValues(conditions);
                }

            }

        }

    }

    /**
     * 普通设计
     * @param clazz
     */
    private static Sort getSort(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(com.demo.elasticsearch.annotations.Sort.class)) {
                Sort sort = new Sort();
                com.demo.elasticsearch.annotations.Sort sortAnno = field.getAnnotation(com.demo.elasticsearch.annotations.Sort.class);
                if(!StringUtils.isEmpty(sortAnno.field())){
                    sort.setField(sortAnno.field());
                }else {
                    sort.setField(field.getName());
                }
                sort.setOrder(sortAnno.order());
                return sort;
            }
        }
        return null;
    }

    /**
     * 普通设计
     * @param clazz
     */
    public static Sort getDeepSort(Class clazz){
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(Function.class)) {
                Function function = field.getAnnotation(Function.class);
                if(StringUtils.isEmpty(function.order())){
                    continue;
                }else {
                   Sort sort = new Sort();
                   if(StringUtils.isEmpty(function.field())){
                        sort.setField(BeanHump.camelToUnderline(field.getName()));
                   }else{
                        sort.setField(function.value().getName()+"_"+function.field());
                   }
                   sort.setOrder(function.order());
                   sort.setStatus(1);
                    return sort;
                }
            }
        }
        return null;
    }

    public static void createPageModel(Method method, Model model,Object[] args) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Map<String,String> params = model.getParams();
        int i = 0;
        for (Annotation[] annotations : parameterAnnotations) {
            for (Annotation annotation : annotations) {
                //获取注解名
                if(annotation.annotationType().equals(Page.class)){
                    params.put("page",Integer.parseInt(args[i]+"")+"");
                }
                if(annotation.annotationType().equals(Limit.class)){
                    params.put("limit",Integer.parseInt(args[i]+"")+"");
                }
            }
            i++;
        }
    }
}
