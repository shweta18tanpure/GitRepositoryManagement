package com.spring.datajpa.gitRepositoryRetriever;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GitRepositoryRetrieverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitRepositoryRetrieverApplication.class, args);
	}

}
