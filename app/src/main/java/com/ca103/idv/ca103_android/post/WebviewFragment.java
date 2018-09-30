package com.ca103.idv.ca103_android.post;

import android.app.Activity;
import android.app.Notification;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.event.EventVO;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class WebviewFragment extends Fragment {
    private static final String TAG = "/eve/EveAndroid.do";

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
        EventVO eventVO = null;
        String eve_id = "E000008";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            // json取得單一活動VO, 其中的content clob測試用webview處理
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "get_one_eve");
            jsonObject.addProperty("eve_id", eve_id);
            String jsonout = jsonObject.toString();
            eventTask = new CommonTask(Util.URL+TAG, jsonout);
            String result = eventTask.execute().get();
            // 將資料從json轉回vo格式
            eventVO = gson.fromJson(result, EventVO.class);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(null, eventVO.getEve_content(),"text/html", "utf-8",null);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                // 在7.0時deprecated(還是有支援)，為了向下版本支援仍用此方法
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        } catch ( Exception e ) {
            Log.e(TAG, e.toString());
        }



        return view;
    }

}
