package com.spring.datajpa.gitRepositoryRetriever.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepositoryDetailsResponse {

    @JsonAlias("full_name")
    private String fullName;
    private String description;
    @JsonAlias("clone_url")
    private String cloneUrl;
    @JsonAlias("stargazers_count")
    private int stars;
    @JsonAlias("created_at")
    private LocalDateTime createdAt;

}
