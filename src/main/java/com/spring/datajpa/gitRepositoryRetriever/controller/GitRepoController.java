package com.spring.datajpa.gitRepositoryRetriever.controller;

import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetailsResponse;
import com.spring.datajpa.gitRepositoryRetriever.exceptionHandler.ErrorResponse;
import com.spring.datajpa.gitRepositoryRetriever.service.GitRepoDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repositories")
public class GitRepoController {
    Logger log = LoggerFactory.getLogger(GitRepoController.class);

    @Autowired
    GitRepoDetailsService repoService;

    @GetMapping("/{owner}/{repository-name}")
    public ResponseEntity<?> getRepositoryDetails(@PathVariable String owner, @PathVariable("repository-name")
                                                  String repoName) {
        ResponseEntity<?> response = null;
        try {
            response = repoService.getGitRepoDetails(owner,repoName);
            if(response.getBody() instanceof GitRepositoryDetailsResponse) {
                GitRepositoryDetailsResponse responseForDBSave = (GitRepositoryDetailsResponse) response.getBody();
                repoService.getSpecifiedRepoDetails(owner,repoName,responseForDBSave);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(),"500"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Resource not found","404"));
        }
        log.info("Sending Response to caller: {}", response);
        return response;
    }
}
