package com.example.tuantran.mvvm_architecture.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tuantran.mvvm_architecture.R;
import com.example.tuantran.mvvm_architecture.service.model.Project;
import com.example.tuantran.mvvm_architecture.view.callback.ProjectClickCallback;
import com.example.tuantran.mvvm_architecture.databinding.ProjectListItemBinding;

import java.util.List;
import java.util.Objects;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

    public List<? extends Project> projectList;
    private ProjectClickCallback projectClickCallBack;


    public ProjectAdapter(ProjectClickCallback projectClickCallBack) {
        this.projectClickCallBack = projectClickCallBack;
    }

    public void setProjectList(final List<? extends Project> projectList) {
        if (this.projectList == null){
            this.projectList = projectList;
            notifyItemRangeInserted(0, projectList.size());
        }else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProjectAdapter.this.projectList.size();
                }

                @Override
                public int getNewListSize() {
                    return projectList.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return ProjectAdapter.this.projectList.get(i).id == projectList.get(i1).id;
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    Project project = projectList.get(i1);
                    Project old = projectList.get(i);
                    return project.id == old.id && Objects.equals(project.git_url, old.git_url);
                }
            });
            this.projectList = projectList;
            result.dispatchUpdatesTo(this);
        }

    }

    @NonNull
    @Override
    public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ProjectListItemBinding binding = DataBindingUtil.inflate(
                                            LayoutInflater.from(viewGroup.getContext()),
                                            R.layout.project_list_item,
                                            viewGroup,
                                            false);

        binding.setCallback(projectClickCallBack);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectViewHolder projectViewHolder, int i) {
        projectViewHolder.binding.setProject(projectList.get(i));
        projectViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return projectList== null ? 0 : projectList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {

        ProjectListItemBinding binding;

        public ProjectViewHolder(ProjectListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
