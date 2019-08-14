package com.example.tuantran.mvvm_architecture.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.example.tuantran.mvvm_architecture.R;
import com.example.tuantran.mvvm_architecture.helper.ExternalStorageHelper;
import com.example.tuantran.mvvm_architecture.helper.Utils;
import com.example.tuantran.mvvm_architecture.service.model.Project;
import com.example.tuantran.mvvm_architecture.view.callback.iProjectDetail;
import com.example.tuantran.mvvm_architecture.viewmodel.ProjectViewModel;
import com.example.tuantran.mvvm_architecture.databinding.FragmentProjectDetailBinding;

import static com.example.tuantran.mvvm_architecture.view.ui.ProjectListFragment.TAG;

public class ProjectFragment extends Fragment implements iProjectDetail {

    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailBinding binding;

    int downloadProject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_detail, container, false);

        // Create and set the adapter for the RecyclerView.
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_PROJECT_ID));

        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory)
                .get(ProjectViewModel.class);

        binding.setProjectViewModel(viewModel);
        binding.setIsLoading(true);
        binding.setIProjectDetail((iProjectDetail) this);


        observeViewModel(viewModel);
    }

    private void observeViewModel(final ProjectViewModel viewModel) {
        // Observe project data
        viewModel.getObservableProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if (project != null) {
                    binding.setIsLoading(false);
                    viewModel.setProject(project);
                }
            }
        });
    }



    /** Creates project fragment for specific project ID */
    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void download() {

        PRDownloader.initialize(getActivity());

        String url = binding.getProjectViewModel().project.get().clone_url;
        Log.d(TAG, "download: "+url);
        String fileName = binding.getProjectViewModel().project.get().name;
        String path = ExternalStorageHelper.createFolder(fileName);
        downloadProject(url,path,fileName);

    }

    private void downloadProject(String url, final String path, String fileName){
        if (Status.RUNNING == PRDownloader.getStatus(downloadProject)){
            PRDownloader.pause(downloadProject);
            return;
        }
         downloadProject = PRDownloader.download(url,path,fileName).build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        Log.d(TAG, "Progress: "+((progress.currentBytes *100)/progress.totalBytes));
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Log.d(TAG, "onDownloadComplete: "+path);
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });
    }
}
