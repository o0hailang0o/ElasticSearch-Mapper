package com.demo.elasticsearch.proxy;

import com.demo.elasticsearch.enums.BaseMethodName;
import com.demo.elasticsearch.handler.ClassHandler;
import com.demo.elasticsearch.handler.HandlerModel;
import com.demo.elasticsearch.handler.HandlerResult;
import com.demo.elasticsearch.methods.BulkSave;
import com.demo.elasticsearch.methods.FindAll;
import com.demo.elasticsearch.methods.FindOne;
import com.demo.elasticsearch.methods.Save;
import com.demo.elasticsearch.model.AggsGroup;
import com.demo.elasticsearch.model.Model;
import com.demo.elasticsearch.model.PageResult;
import com.demo.elasticsearch.model.Search;
import com.demo.elasticsearch.repository.ElasticSearchBaseRepository;
import com.demo.elasticsearch.start.EsUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author zhaoyong
 * @version $Id: ElasticRepositoryProxy , v 0.1  K555 Exp $
 * @date 2019年01月30 16:20
 */
public class ElasticRepositoryProxy<T> implements InvocationHandler {

    private final Search search;

    private final Class<T> mapperInterface;

    public ElasticRepositoryProxy(Search search, Class<T> mapperInterface) {
        this.search = search;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args){
        if(method.getDeclaringClass().equals(ElasticSearchBaseRepository.class)){
            return getMethod(method,args);
        }
        return getCommonMethod(method,args);
    }

    private Object getMethod(Method method, Object[] args) {
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        EsUtils esUtils = SpringUtil.getBean(EsUtils.class);
        Class<T> clazz = getTClass();
        if(method.getName().equals(BaseMethodName.FIND_ALL.getName())){
            return new FindAll<T>().handlerRest(clazz,esUtils,args);
        }
        if(method.getName().equals(BaseMethodName.SAVE.getName())){
            return new Save<T>().handlerRest(clazz,esUtils,args);
        }
        if(method.getName().equals(BaseMethodName.FIND_ONE.getName())){
            return new FindOne<T>().handlerRest(clazz,esUtils,args);
        }
        if(method.getName().equals(BaseMethodName.BULK_SAVE.getName())){
            return new BulkSave<T>().handlerRest(clazz,esUtils,args);
        }
        return null;
    }


    private Object getCommonMethod(Method method,Object[] args){
        Model model = getIndexAndType();
        Type type = method.getGenericReturnType();
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type[] types = parameterizedType.getActualTypeArguments();
            HandlerModel.createModel(model,args);
            if (parameterizedType.equals(PageResult.class)) {
                return search.search(model);
            } else if (types.length==1) {
                //返回List情况下 聚合查询 以及 排序返回结果 也会影响到查询结果
                Class<?> resultClass = (Class)types[0];
                return  getResult(resultClass,model);
            }
        } else {
            Class<?> resultClass = (Class)type;
            List<?> resultList = getResult(resultClass,model);
            if(!CollectionUtils.isEmpty(resultList)){
                return resultList.get(0);
            }else {
                return null;
            }
        }
        return null;
    }

    private List<?> getResult( Class<?> resultClass, Model model){
        AggsGroup aggsGroup = HandlerModel.getAggsGroup(resultClass);
        model.setAggs(aggsGroup);
        PageResult pageResult = search.search(model);
        return HandlerResult.resultMap(pageResult,resultClass);
    }

    private Model getIndexAndType(){
        Model model = new Model();
        Class<?> clazz = getTClass();
        String index = ClassHandler.getIndex(clazz);
        String type = ClassHandler.getType(clazz);
        model.setType(type);
        model.setIndex(index);
        return model;
    }

    private Class<T> getTClass(){
        Type[] proxyType = mapperInterface.getGenericInterfaces();
        ParameterizedType p=(ParameterizedType)proxyType[0];
        //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        return ( Class<T>) p.getActualTypeArguments()[0];
    }
}
