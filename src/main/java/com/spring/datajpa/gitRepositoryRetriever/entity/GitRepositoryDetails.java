package com.spring.datajpa.gitRepositoryRetriever.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "gitRepoDtls")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GitRepositoryDetails {

    @Id
    @GeneratedValue
    private Integer id;
    private String ownerName;
    private String repoName;
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDateTime createdAt;

}
