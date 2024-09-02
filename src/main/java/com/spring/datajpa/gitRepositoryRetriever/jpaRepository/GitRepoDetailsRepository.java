package com.spring.datajpa.gitRepositoryRetriever.jpaRepository;

import com.spring.datajpa.gitRepositoryRetriever.entity.GitRepositoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GitRepoDetailsRepository extends JpaRepository<GitRepositoryDetails, Integer> {
    @Query("select r from gitRepoDtls r where r.ownerName = :ownerName and r.repoName = :repoName")
    GitRepositoryDetails findByOwnerNameAndRepoName(@Param("ownerName") String ownerName,
                                                    @Param("repoName") String repoName);

}
