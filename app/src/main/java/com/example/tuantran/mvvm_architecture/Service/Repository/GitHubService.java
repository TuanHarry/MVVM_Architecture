package com.example.tuantran.mvvm_architecture.Service.Repository;

import com.example.tuantran.mvvm_architecture.Service.Model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {

    /*
    * In order to interacts with REST API github with github username
    *
    * */

    String GITHUB_API_URL = "https://api.github.com/";

    // Get list project from github
    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    // Get project detail
    @GET("repos/{user}/{reponame}")
    Call<Project> getProjectDetail(@Path("user") String user, @Path("reponame") String projectName);


}
