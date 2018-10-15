package com.ca103.idv.ca103_android.post;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;


public class WebviewFragment extends Fragment {
    private static final String TAG = "/front_end/stream/streamChat.jsp";

    public WebView webView;
    public CommonTask eventTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.webview_event, container, false);
        webView = view.findViewById(R.id.webView);
        Activity a = getActivity();
        Util.showToast(getActivity(),a.getLocalClassName());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Util.URL+TAG);
        //            webView.loadDataWithBaseURL(null, eventVO.getEve_content(),"text/html", "utf-8",null);
        //            webView.setWebViewClient(new WebViewClient() {
        //                @Override
        //                // 在7.0時deprecated(還是有支援)，為了向下版本支援仍用此方法
        //                public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //                    view.loadUrl(url);
        //                    return true;
        //                }
        //            });



        return view;
    }

}
