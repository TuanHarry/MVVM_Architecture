package com.example.tuantran.mvvm_architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tuantran.mvvm_architecture.service.model.Project;
import com.example.tuantran.mvvm_architecture.service.repository.ProjectRepository;

import static com.example.tuantran.mvvm_architecture.view.ui.ProjectListFragment.TAG;

public class ProjectViewModel extends AndroidViewModel {
    private final LiveData<Project> projectObservable;
    private final String projectID;

    public ObservableField<Project> project = new ObservableField<>();

    public ProjectViewModel(@NonNull Application application,
                            final String projectID) {
        super(application);
        this.projectID = projectID;

        projectObservable = ProjectRepository.getInstance().getProjectDetail("TuanHarry", projectID);
    }

    public LiveData<Project> getObservableProject() {
        return projectObservable;
    }

    public void setProject(Project project) {
        this.project.set(project);
    }

    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String projectID;

        public Factory(@NonNull Application application, String projectID) {
            this.application = application;
            this.projectID = projectID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProjectViewModel(application, projectID);
        }
    }
}
