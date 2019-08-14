package com.example.tuantran.mvvm_architecture.helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import static com.example.tuantran.mvvm_architecture.view.ui.ProjectListFragment.TAG;

public class ExternalStorageHelper {

    private static File file;

    public static String createFolder (String fileName){
        file = Environment.getExternalStorageDirectory();
        String path = file.getAbsolutePath()+"/GitAPP/"+fileName;
        Log.d(TAG, "writeFile: "+path);
        return path;
    }
}
