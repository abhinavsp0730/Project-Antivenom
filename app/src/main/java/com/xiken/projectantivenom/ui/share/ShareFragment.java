package com.xiken.projectantivenom.ui.share;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiken.projectantivenom.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
//        View root = inflater.inflate(R.layout.activity_maps, container, false);
//        final TextView textView = root.findViewById(R.id.text_share);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,"check this app at https://play.google.com/store/apps/details?id=com.xiken.projectantivenom");
        intent.setType("text/plain");
        startActivity(intent);
        return null;
    }
}