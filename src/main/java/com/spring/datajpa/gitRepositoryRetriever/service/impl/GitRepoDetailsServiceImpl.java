package com.spring.datajpa.gitRepositoryRetriever.service.impl;

import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetails;
import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetailsResponse;
import com.spring.datajpa.gitRepositoryRetriever.jpaRepository.GitRepoDetailsRepository;
import com.spring.datajpa.gitRepositoryRetriever.service.GitHubRepoService;
import com.spring.datajpa.gitRepositoryRetriever.service.GitRepoDetailsService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ObjectUtils;

public class GitRepoDetailsServiceImpl implements GitRepoDetailsService {
    Logger log = LoggerFactory.getLogger(GitRepoDetailsServiceImpl.class);
    @Autowired
    private GitRepoDetailsRepository gitRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private GitHubRepoService gitRepoService;

    @Async
    public void getSpecifiedRepoDetails(String ownerName, String repoName, GitRepositoryDetailsResponse response) {
        GitRepositoryDetails repo = null;
        repo = gitRepo.findByOwnerNameAndRepoName(ownerName, repoName);
        if(repo == null || ObjectUtils.isEmpty(repo)) {
            repo = new GitRepositoryDetails();
            repo.setOwnerName(ownerName);
            repo.setRepoName(repoName);
            repo.setFullName(response.getFullName());
            repo.setDescription(response.getDescription());
            repo.setCloneUrl(response.getCloneUrl());
            repo.setStars(response.getStars());
            repo.setCreatedAt(response.getCreatedAt());
            gitRepo.save(repo);
            log.info("Data Saved in DB for owner={} and Repo={}",ownerName,repoName);
        } else {
            log.info("Record Already exist in DB for owner={} and Repo={}",ownerName,repoName);
        }
    }

    public ResponseEntity<?> getGitRepoDetails(String owner, String repo) {
        return gitRepoService.getRepoData(owner, repo);
    }
}
