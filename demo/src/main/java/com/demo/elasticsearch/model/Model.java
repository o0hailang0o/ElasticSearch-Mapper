package com.demo.elasticsearch.model;

import java.util.Map;
import java.util.Set;

/**
 * @author liujian on 2019/1/4.
 */
public class Model {

        private String index;
        private String type;
        /**
         * 前端可以根据组件 组件conditions（查询条件）
         *
         * 前端组件 select 都是精确查询 term
         *
         * 前端组件 input text 都是模糊查询 phrase
         *
         * 前端组件 input radio 也是term
         *
         * 前端组件 input checkbox 是或多选terms查询
         *
         * 前端conditions提交格式 应该是 [{field1,term},{field2,phrase}]
         *
         * 还有 不等于传mustNotTerm
         *
         * 大于 rangeLt 大于等于rangeLte
         *
         * 小于 rangeGt 大于等于rangeGte
         *
         * 组件拼接只是理想化 如果出现 大于 不等于情况 可能就有问题
         */
        private Set<Condition> conditions;
        /**
         * 分组查询 查询组数量 cardinality
         *
         * 分组查询 查询每组的数量 group
         *
         * 求总和 sum
         *
         * 求最小值 min
         *
         * 求最大值 max
         *
         * 这个版本 树形结构只有一个分支 不支持多分支
         *
         * {groups:[field1,field2,field3],value[min,max,detail]}
         */
        private AggsGroup aggs;
        /**
         * 请求的具体参数
         */
        private Map<String,String> params;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public Set<Condition> getConditions() {
            return conditions;
        }

        public void setConditions(Set<Condition> conditions) {
            this.conditions = conditions;
        }

        public AggsGroup getAggs() {
            return aggs;
        }

        public void setAggs(AggsGroup aggs) {
            this.aggs = aggs;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Model{" +
                    "index='" + index + '\'' +
                    ", conditions=" + conditions +
                    ", aggs=" + aggs +
                    ", params=" + params +
                    '}';
        }
}

