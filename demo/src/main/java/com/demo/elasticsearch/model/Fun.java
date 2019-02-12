package com.demo.elasticsearch.model;

/**
 * @author liujian on 2019/1/28.
 */
public enum Fun {

        SUM("sum"),
        MIN("min"),
        MAX("max"),
        COUNT("count"),
        AVG("avg");

        private String name;

        private Fun(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }


}
