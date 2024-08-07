package com.recrutTask.GithubListing.controllers;


import com.recrutTask.GithubListing.exceptions.UserNotFoundException;
import com.recrutTask.GithubListing.models.Repository;
import com.recrutTask.GithubListing.services.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repos")
public class GithubController {

    @Autowired
    private GithubService gitHubService;

    @GetMapping("/{username}")
    public ResponseEntity<List<Repository>> getRepositories(@PathVariable String username) {
        List<Repository> repositories = gitHubService.getRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}