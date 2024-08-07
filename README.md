# GithubListing

## Table of Contents
* [General info](#general-info)
* [Features](#features)
* [API Endpoints](#api-endpoints)
* [Setup](#setup)
* [Tech Stack](#tech-stack)


## General Info
GithubListing is a Java-based API that lists all non-fork repositories of a given GitHub user, providing detailed information about each repository and its branches.

## Features
* **Non-fork Repository Listing:** Fetches and displays all public, non-fork repositories for a given GitHub username.
* **Branch Details:** Lists each branch name and its latest commit SHA.
* **Error Handling:** Returns a 404 response for non-existing GitHub users with a detailed error message.

## API Endpoints
### Get User Repositories
* **URL:** /repos/{username}
* **Method:** GET
* **Response:**
```
[
  {
    "name": "repo-name",
    "owner": {
      "login": "userlogin"
    },
    "fork": false,
    "branches": [
      {
        "name": "branch-name",
        "commit": {
          "sha": "branch-sha"
        }
      }
    ]
  },
...
]
```

### Error Response for Non-existing User
**Response:**
```
{
  "status": 404,
  "message": "User not found"
}
```

## Setup

### 1. Clone the repository:
```
git clone https://github.com/PiotrKupis999/GithubListing
```

### 2. Build the project using Maven:
```
mvn clean install
```

### 3. Run the application

The API will be available at http://localhost:8080.

You can type into your browser http://localhost:8080/repos/username

## Tech Stack
* Java 21
* SpringBoot (3.3.2)
