package com.spring.datajpa.gitRepositoryRetriever.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface GitHubRepoService {
    ResponseEntity<?> getRepoData(String owner, String repo);
}
