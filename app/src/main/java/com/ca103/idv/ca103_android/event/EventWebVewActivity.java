package com.ca103.idv.ca103_android.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.ca103.idv.ca103_android.R;

public class EventWebVewActivity extends AppCompatActivity {

    private WebView wvEvent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_event);
        wvEvent = (WebView) findViewById(R.id.webView);
        String content = savedInstanceState.getString("content");
        wvEvent.loadDataWithBaseURL
                (null, content, "text/html", "utf-8", null);
    }

    // 改寫返回鍵事件, 實作單一檢視後回到上一個視窗, 且專注在上一個選擇項
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;// return false 或者return true 都不会走onBackPressed了
        }
        return false;
    }

}
