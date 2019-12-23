package com.xiken.projectantivenom.ui.support;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.xiken.projectantivenom.R;

public class SupportViewFragment extends Fragment {
    private SupportViewModel supportViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        supportViewModel = ViewModelProviders.of(this).get(SupportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_support,container,false);
        return root;

    }
}
