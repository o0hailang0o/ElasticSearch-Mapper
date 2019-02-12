package com.demo.elasticsearch.enums;

/**
 * @author liujian on 2019/2/12.
 */
public enum  BaseMethodName {

    FIND_ALL("findAll"),
    FIND_ONE("findOne"),
    SAVE("save"),
    BULK_SAVE("bulkSave");

    private String name;

    private BaseMethodName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
