package com.example.tuantran.mvvm_architecture.Service.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.tuantran.mvvm_architecture.Service.Model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class ProjectRepository {
    /*
    * */
    private GitHubService gitHubService;
    private static ProjectRepository projectRepository;

    // Init retrofit
    private ProjectRepository(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.GITHUB_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(GitHubService.class);
    }


    public synchronized static ProjectRepository getInstance(){
        if (projectRepository == null){
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

    public LiveData<List<Project>> getProjectList(String userID){
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();
        gitHubService.getProjectList(userID).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, Response<List<Project>> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                data.setValue(null);
                Log.d(TAG, "onFailure: ");
            }
        });

        return data;
    }

    public LiveData<Project> getProjectDetail(String userID, String projectName){
        final MutableLiveData<Project> data = new MutableLiveData<>();
        gitHubService.getProjectDetail(userID, projectName).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    private void simulateDelay(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
