package com.xiken.projectantivenom.ui.web;

import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.xiken.projectantivenom.R;


public class WebV extends Fragment {
    WebViewModel galleryViewModel;
    WebView webView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(WebViewModel.class);
        View root = inflater.inflate(R.layout.web_v,container,false);
        webView = root.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://agricoop.nic.in/programmes-schemes-listing");

        return root;
    }
}
