package com.demo.elasticsearch.model;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujian on 2019/1/14.
 */
public class PageResult {

    private List<JSONObject> jsonObjectList = new ArrayList<>();

    private Long count = 0L;

    public List<JSONObject> getJsonObjectList() {
        return jsonObjectList;
    }

    public void setJsonObjectList(List<JSONObject> jsonObjectList) {
        this.jsonObjectList = jsonObjectList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "jsonObjectList=" + jsonObjectList +
                ", count=" + count +
                '}';
    }
}
