package com.recrutTask.GithubListing.services;


import com.recrutTask.GithubListing.exceptions.UserNotFoundException;
import com.recrutTask.GithubListing.models.Branch;
import com.recrutTask.GithubListing.models.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GithubService {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    private static final String GITHUB_API_URL = "https://api.github.com";

    @Autowired
    private RestTemplate restTemplate;

    public List<Repository> getRepositories(String username) {
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        ResponseEntity<Repository[]> response;

        try {
            response = restTemplate.getForEntity(url, Repository[].class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User not found");
            } else {
                throw e;
            }
        }

        Repository[] repositories = response.getBody();
        if (repositories == null) {
            return List.of();
        }

        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(repo -> {
                    String branchesUrl = GITHUB_API_URL + "/repos/" + username + "/" + repo.getName() + "/branches";
                    ResponseEntity<Branch[]> branchesResponse = restTemplate.getForEntity(branchesUrl, Branch[].class);
                    repo.setBranches(Arrays.asList(branchesResponse.getBody()));
                    return repo;
                })
                .collect(Collectors.toList());
    }
}