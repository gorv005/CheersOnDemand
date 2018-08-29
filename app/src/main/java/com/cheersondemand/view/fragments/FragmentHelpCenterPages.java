package com.cheersondemand.view.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cheersondemand.R;
import com.cheersondemand.model.HelpCenter.HelpCenterList;
import com.cheersondemand.util.C;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHelpCenterPages extends Fragment {


    @BindView(R.id.webview)
    WebView webview;
    Unbinder unbinder;
    HelpCenterList helpCenterList;
    Util util;
    public FragmentHelpCenterPages() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helpCenterList=(HelpCenterList)getArguments().getSerializable(C.HELP_ITEM);
        util=new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_center_pages, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ActivityContainer)getActivity()).setTitle(helpCenterList.getName());

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        util.showDialog(C.MSG,getActivity());
        webview.setWebViewClient(new CustomWebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(helpCenterList.getLink());

    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {


            util.hideDialog();
            super.onPageFinished(view, url);

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
