package com.example.tuantran.mvvm_architecture.view.adapter;

import android.databinding.BindingAdapter;
import android.view.View;

public class CustomBidingAdapter {
    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
