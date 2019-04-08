es-mapper 简单开发es的查询接口，现在第一版本是简单的查询
最主要就是注解理解

框架搭建，你可以封装成maven，也可以copy到你的springboot工程。

--------------------------
首先pom文件依赖（前提是springboot工程，Elasticsearch版本在5.0以后）
<!-- https://mvnrepository.com/artifact/org.elasticsearch.client/rest -->
		<dependency>
			<groupId>org.elasticsearch.client</groupId>
			<artifactId>rest</artifactId>
			<version>6.0.0-alpha2</version>
		</dependency>

		<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
			<version>1.2.47</version>
		</dependency>
		<dependency>
			<groupId>commons-io</groupId>
			<artifactId>commons-io</artifactId>
			<version>2.4</version>
		</dependency>
其次在启动Application类
加上注解@ElasticRepositoryScan(value = "com.demo.repository", searchRef = "search")
value代表的该框架扫描包，后面固定写法

XXX表示索引的mapping映射的实体bean
其次就是 每个XXXRepository接口 加上注解@EsMapper 以及继承ElasticSearchRepository<XXX>
XXX当中 加上Document注解表明 type 以及 index  注意index是有版本号。建立的索引都是XXX-v(EsVersion)
必须表明@Id
--------------------------
上面的步骤 完整开发流程

开发最重要的就是写在DTO的注解
@Condition
    field:代表的索引的字段名称，默认值是DTO中字段名
    value:代表索引对这个字段筛选条件，默认是value="="，其他有 模糊搜索 like 大于小于 不等于 。。
    mapping:是映射字段名称的意思，
            如果DTO的名称与索引名称不一致，mapping代表的映射的意思。
            必须与DTO字段名称一致

@Function
    field:代表对索引某个字段名计算，求和，平均值，最大值，最小值等
    value:enum中FUN
    order:对计算后排序

@Group 相当于sql的group by
    field:对索引某个字段分组查询
    order:因为分组是嵌套的，order大小代表了嵌套的前后顺序，数字越小嵌套越先执行分组
    key:代表分组别名
    condition:分组条件，根据EL表达式表示是否对该字段分组

@Result
    value:映射结果字段
@Sort
    field:对该索引的某个字段排序，默认是DTO字段名称，
    order:asc desc默认是asc。


分页计算
接口方法参数 @Page @Limit 代表分页
PageData是分页返回值 只有接口返回值PageData分页注解才能生效



