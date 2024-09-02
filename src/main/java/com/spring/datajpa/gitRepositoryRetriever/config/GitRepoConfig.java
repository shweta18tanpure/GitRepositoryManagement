package com.spring.datajpa.gitRepositoryRetriever.config;

import com.spring.datajpa.gitRepositoryRetriever.service.GitHubRepoService;
import com.spring.datajpa.gitRepositoryRetriever.service.GitRepoDetailsService;
import com.spring.datajpa.gitRepositoryRetriever.service.impl.GitHubServiceImpl;
import com.spring.datajpa.gitRepositoryRetriever.service.impl.GitRepoDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GitRepoConfig {
    @Bean
    public GitRepoDetailsService gitRepoDtlsBean() {
        return new GitRepoDetailsServiceImpl();
    }

    @Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }

    @Bean
    public GitHubRepoService gitHubRepoBean() {
        return new GitHubServiceImpl();
    }

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
