package com.spring.datajpa.gitRepositoryRetriever.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetailsResponse;
import com.spring.datajpa.gitRepositoryRetriever.exceptionHandler.ErrorResponse;
import com.spring.datajpa.gitRepositoryRetriever.service.GitHubRepoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static com.spring.datajpa.gitRepositoryRetriever.util.Constants.*;

public class GitHubServiceImpl implements GitHubRepoService {
    Logger log = LoggerFactory.getLogger(GitHubServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${rest.endpoint.git.getaRepo.base.url}")
    private String gitGetRepoUrl;

    //Note: Following method will get public repositories only, as no authentication done
    public ResponseEntity<?> getRepoData(String owner, String repo) {
        String url = gitGetRepoUrl + owner + "/" + repo;
        // String response = restTemplate.getForObject(url,String.class);  //Note: This also works, but added some headers as mentioned in GIT API documentation
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, ACCEPT_VAL);
        // headers.set(HttpHeaders.AUTHORIZATION, BEARER + token);  //Note: skipping this, public repos can be accessed anyway(it was there in curl cmd in API doc)
        headers.set(GIT_API_VERSION, GIT_API_VERSION_VAL); // Note: Optional

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            String responseBody = response.getBody();
            log.info("Response from git repo api: {}", responseBody);

            return new ResponseEntity<>(getRepositoryResponse(responseBody), HttpStatus.OK);
        } catch (Exception ex) {
            String message = ex.getMessage();
            log.error("Problem occurred while invoking git repo api: message= {}", message);
            return new ResponseEntity<>(getRepositoryErrorResponse(message), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GitRepositoryDetailsResponse getRepositoryResponse(String responseMsg) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());
        GitRepositoryDetailsResponse repoRes = objMapper.readValue(responseMsg, GitRepositoryDetailsResponse.class);
        return repoRes;
    }

    private ErrorResponse getRepositoryErrorResponse(String responseMsg) {
        ErrorResponse repoRes = null;
        String requiredMsg = responseMsg.substring(responseMsg.indexOf("{"));
        try {
            repoRes = new ObjectMapper().readValue(requiredMsg, ErrorResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return repoRes;
    }

}
