package com.demo.elasticsearch.model;

import java.util.List;

/**
 * @author liujian on 2019/3/23.
 */
public class PageData<T> {

    private Long count;

    private List<T> dataList;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Page{" +
                "count=" + count +
                ", dataList=" + dataList +
                '}';
    }
}
