package com.demo;

import com.demo.elasticsearch.proxy.ElasticRepositoryScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ElasticRepositoryScan(value = "com.demo.repository", searchRef = "search")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
