package com.spring.datajpa.gitRepositoryRetriever.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetailsResponse;
import org.springframework.http.ResponseEntity;

public interface GitRepoDetailsService {
    void getSpecifiedRepoDetails(String owner, String repo, GitRepositoryDetailsResponse response);
    ResponseEntity<?> getGitRepoDetails(String owner, String repo) throws JsonProcessingException;
}
