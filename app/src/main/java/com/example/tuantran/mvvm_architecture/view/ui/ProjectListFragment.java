package com.example.tuantran.mvvm_architecture.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tuantran.mvvm_architecture.R;
import com.example.tuantran.mvvm_architecture.service.model.Project;
import com.example.tuantran.mvvm_architecture.view.adapter.ProjectAdapter;
import com.example.tuantran.mvvm_architecture.view.callback.ProjectClickCallback;
import com.example.tuantran.mvvm_architecture.viewmodel.ProjectListViewModel;
import com.example.tuantran.mvvm_architecture.databinding.FragmentProjectListBinding;

import java.util.List;

public class ProjectListFragment extends Fragment {

    public static final String TAG = "ProjectListFragment";
    FragmentProjectListBinding binding;
    ProjectAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Binding to view
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list,container,false);

        adapter = new ProjectAdapter(projectClickCallback);
        binding.projectList.setAdapter(adapter);
        binding.setIsLoading(true);

        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProjectListViewModel viewModel =
                ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }

    private void observeViewModel(ProjectListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
                    binding.setIsLoading(false);
                    adapter.setProjectList(projects);
                }
            }
        });
    }

    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(project);
            }
        }
    };
}
