package com.demo.elasticsearch.model;

import java.util.List;

/**
 * @author liujian on 2019/1/7.
 */
public class AggsGroup {

    private List<GroupBy> groups;

    private List<Condition> values;

    public List<GroupBy> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBy> groups) {
        this.groups = groups;
    }

    public List<Condition> getValues() {
        return values;
    }

    public void setValues(List<Condition> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "AggsGroup{" +
                "groups=" + groups +
                ", values=" + values +
                '}';
    }
}
