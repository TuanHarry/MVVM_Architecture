package com.example.tuantran.mvvm_architecture.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tuantran.mvvm_architecture.Service.Model.Project;
import com.example.tuantran.mvvm_architecture.Service.Repository.ProjectRepository;

import java.util.List;

public class ProjectListViewModel extends AndroidViewModel {

    LiveData<List<Project>> projectListObservable;

    public ProjectListViewModel(@NonNull Application application) {
        super(application);

        projectListObservable = ProjectRepository.getInstance().getProjectList("TuanHarry");
    }

    // Update UI

    public LiveData<List<Project>> getProjectListObservable(){
        return projectListObservable;
    }
}
