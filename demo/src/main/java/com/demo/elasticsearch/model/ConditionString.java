package com.demo.elasticsearch.model;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liujian on 2019/1/4.
 */
public class ConditionString {

    private Model model;

    public ConditionString(){

    }

    public ConditionString(Model model){
        this.model = model;
    }

    private String getConditionFilter(){
        String filter ="";
        if(model!=null){
            Set<Condition> conditions =  model.getConditions();
            if(!CollectionUtils.isEmpty(conditions)){
                for(Condition condition : conditions){
                    String field = condition.getField();
                    String mapping = condition.getMapping();
                    if(StringUtils.isEmpty(mapping)){
                        mapping = field;
                    }
                    if(model.getParams()!=null && !StringUtils.isEmpty(model.getParams().get(mapping))){
                        filter+=getFilter(condition);
                    }
                }
            }
        }
        if(StringUtils.isEmpty(filter)){
            return "";
        }
        return filter.substring(1);
    }

    private String getFilter(Condition condition){
        String value = condition.getValue();
        Map<String, String> params = model.getParams();
        String mapping = condition.getMapping();
        if(StringUtils.isEmpty(mapping)){
            mapping = condition.getField();
        }
        switch(value){
            case "=":
                return  termString(condition.getField(),params.get(mapping));
            case "like":
                return  matchPhraseString(condition.getField(),params.get(mapping));
            case "match":
                return  matchString(condition.getField(),params.get(mapping));
            case "in":
                return  termsString(condition.getField(),params.get(mapping).split(","));
            case "!=":
                return  boolMustNotString(condition.getField(),params.get(mapping));
            case ">":
                return rangeString(condition.getField(),params.get(mapping),"gt");
            case ">=":
                return rangeString(condition.getField(),params.get(mapping),"gte");
            case "<":
                return rangeString(condition.getField(),params.get(mapping),"lt");
            case "<=":
                return rangeString(condition.getField(),params.get(mapping),"lte");
            default:
                return  termString(condition.getField(),params.get(mapping));
        }
    }



    public String getDslQuery(){
        Integer from = 1;
        Integer size = 10;
        String aggs = "";
        if(model!= null){
            Map<String, String> params = model.getParams();
            if(params == null){
                params = new HashMap<>(16);
            }
            Integer limit = StringUtils.isEmpty(params.get("limit"))?10000:Integer.parseInt(params.get("limit"));
            Integer page =  StringUtils.isEmpty(params.get("page"))?1:Integer.parseInt(params.get("page"));
            from = (page-1)*limit;
            size = limit;
            if(model.getAggs()!=null&&!(CollectionUtils.isEmpty(model.getAggs().getGroups())&& CollectionUtils.isEmpty(model.getAggs().getValues()))){
                size = 0;
                aggs = getConditionAggs();
            }
        }
        String dsl = "{\n" +
                "  \"from\": "+from+",\n"+
                "  \"size\": "+size+",\n"+
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"must\": [\n" ;
                 dsl+=getConditionFilter();
        dsl+=  "      ]\n" +
                "    }\n" +
                "  }\n";
        if(model.getSort()!=null&&(model.getSort().getStatus()==null||model.getSort().getStatus().equals(0))){
            dsl+=easySort(model.getSort());
        }
        dsl+=aggs;
        dsl+= "}";
        return dsl;
    }

    private String getConditionAggs() {
        //-1注意啦 这是要求 父级必须group形式 最后一个聚合 才是所要求的最大值 最小值 数量 详情等等
        return getAggsString(0);
    }


    private String getAggsString(int i){
        String aggs = "";
        AggsGroup aggsGroup = model.getAggs();
        List<GroupBy> groups = aggsGroup.getGroups();
        if(groups!=null&&!groups.isEmpty()){
             GroupBy groupBy = groups.get(i);
             String key = groupBy.getField();
             if(!StringUtils.isEmpty(groupBy.getKey())){
                 key = groupBy.getKey();
             }
             aggs+=",\"aggs\": {\n" +
                    "      \"group_"+key+"\":{\n" +
                    "        \"terms\": {\n" +
                    "          \"field\": \""+groupBy.getField()+"\",\n" +
                    "          \"execution_hint\": \"map\",\n"+
                    "          \"size\": 10000\n";
                    if(i==groups.size()-1){
                        if(model.getSort()!=null&&model.getSort().getStatus().equals(1)){
                            aggs+=deepSort(model.getSort());
                        }
                    }
                aggs+= "       }\n" ;
            if(i<groups.size()-1){
                aggs+=getAggsString(i+1);
            }
            if(i == groups.size()-1){
                aggs+=getValues();
            }
            aggs+=  "    }\n" +
                    "  }\n";
        }else {
            aggs+=getValues();
        }
        return aggs;
    }

    private String getValues(){
        String aggs = ",\"aggs\": {\n";
        AggsGroup aggsGroup = model.getAggs();
        List<Condition> conditions = aggsGroup.getValues();
        if(conditions!=null) {
            for (int i=0;i<conditions.size();i++) {
                //min max sum count avg
                Condition con = conditions.get(i);
                String value = con.getValue();
                String field = con.getField();
                if(i!=0){
                    aggs+=",";
                }
                aggs += "\"" + value + "_" + field + "\":{\n" +
                        "   \"" + mapValue(value) + "\": {\n" +
                        "       \"field\": \"" + field + "\"\n" +
                        "     }\n" +
                        " }\n";
            }
        }
        aggs+="}\n";
        return aggs;
    }

    private String mapValue(String value){
        switch(value){
            case "sum":
                return  "sum";
            case "min":
                return  "min";
            case "max":
                return  "max";
            case "avg":
                return  "avg";
            case "count":
                return "cardinality";
            default:
               throw new RuntimeException("无法找到该计算方式");
        }
    }

    private String termString(String field,Object value){
        return  ",{\n" +
                "         \"term\": {\n" +
                "           \""+field+"\": {\n" +
                "             \"value\": \""+value+"\"\n" +
                "           }\n" +
                "         }\n" +
                "       }";
    }

    private String termsString(String field,Object[] values){
        String str =  ",{\n" +
                "   \"terms\": {\n" +
                "         \""+field+"\": [\n" ;
                for(int i=0;i<values.length;i++){
                    if(i!=0){
                        str+=",";
                    }
                    str+="\""+values[i]+"\"";
                }
            str+=      "      ]\n" +
                "    }\n" +
                " }";
            return str;
    }

    private String matchPhraseString(String field,Object value){
        return ",{\n" +
                "     \"match_phrase\": {\n" +
                "          \""+field+"\": \""+value+"\"\n" +
                "      }\n" +
                "  }";
    }

    private String matchString(String field,Object value){
        return ",{\n" +
                "     \"match\": {\n" +
                "          \""+field+"\": \""+value+"\"\n" +
                "      }\n" +
                "  }";
    }


    private String boolMustNotString(String field,String value){
        return " ,\"bool\": {\n" +
                "             \"must_not\" : { \n" +
                "               \"term\" : { \n" +
                "                 \""+field+"\" : \n" +
                "                 \""+value+"\" \n" +
                "               } \n" +
                "             }\n" +
                "          }";
    }

    private String rangeString(String field,String value,String cal){
        return ",{\n" +
                "     \"range\": {\n" +
                "        \""+field+"\": {\n" +
                "          \""+cal+"\": \""+value+"\"\n" +
                "         }\n" +
                "       }\n" +
                "   }      ";
    }

    private String easySort(Sort sort){
        return ",\"sort\": [\n" +
                "    {\n" +
                "      \""+sort.getField()+"\": {\n" +
                "        \"order\": \""+sort.getOrder()+"\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n";
    }

    private String deepSort(Sort sort){
        return "           ,\"order\": {\n" +
                "                 \""+sort.getField()+"\": \""+sort.getOrder()+"\"\n" +
                "           }\n";
    }

}
