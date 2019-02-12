package com.demo.elasticsearch.start;

import com.demo.elasticsearch.proxy.SpringUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Import(SpringUtil.class)
@Configuration
public class ElasticsearchConfiguration  {

    @Bean
    public RestClient getRestClient(){
        List<HttpHost> httpHostList = new ArrayList<HttpHost>();
        String[] hostNames = esHostname.split(";");
        String[] ports = esPort.split(";");
        String[] schemes = schema.split(";");
        for (int i = 0; i < hostNames.length; i++) {
            httpHostList.add(new HttpHost(hostNames[i], Integer.valueOf(ports[i]), schemes[i]));
        }
        HttpHost[] httpHosts = new HttpHost[httpHostList.size()];
        return  RestClient.builder(httpHostList.toArray(httpHosts)).build();
    }

    @Value("${es.hostname}")
    private String esHostname;

    @Value("${es.port}")
    private String esPort;

    @Value("${es.schema}")
    private String schema;

}
