package com.ca103.idv.ca103_android.member;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.*;

import java.io.Console;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private TextView tvMessage;
    private CommonTask isMemberTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvMessage = findViewById(R.id.tvMessage);
        setResult(RESULT_CANCELED);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE,
                MODE_PRIVATE);
        boolean login = false;
                //preferences.getBoolean("login", false);
        if (login) {
            String userId = preferences.getString("account", "");
            String password = preferences.getString("password", "");
            if (isMember(userId, password)) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    public void onLoginClick(View view) {
        EditText etUser = findViewById(R.id.etUser);
        EditText etPassword = findViewById(R.id.etPassword);
        String userId = etUser.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (userId.length() <= 0 || password.length() <= 0) {
            showMessage(R.string.msg_InvalidUserOrPassword);
            return;
        }

        if (isMember(userId, password)) {
            SharedPreferences preferences = getSharedPreferences(
                    Util.PREF_FILE, MODE_PRIVATE);
            preferences.edit().putBoolean("login", true)
                    .putString("account", userId)
                    .putString("password", password).apply();
            setResult(RESULT_OK);
            finish();
        } else {
            showMessage(R.string.msg_InvalidUserOrPassword);
        }
    }

    private boolean isMember(final String account, final String password) {
        boolean isMember = false;
        MemVO memVO = null;
        SharedPreferences preferences = getSharedPreferences(
                Util.PREF_FILE, MODE_PRIVATE);

        if (Util.networkConnected(this)) {
            String url = Util.URL + "/mem/memAndroid.do";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "login");
            jsonObject.addProperty("account", account);
            jsonObject.addProperty("password", password);
            String jsonOut = jsonObject.toString();
            isMemberTask = new CommonTask(url, jsonOut);
            try {
                String result = isMemberTask.execute().get();
                Util.showToast(this, result);
                memVO = new Gson().fromJson(result, MemVO.class);
                // 若無法取得會員VO物件, 代表無此會員
                // 將登入狀態設為false
                if ("".equals(result)) {
                    Util.showToast(this, R.string.msg_NoProfileFound);
                    isMember = false;
                }
                else {
                    Util.showToast(this, "success");
                    //preferences.edit().putString("memVO", result);
                    isMember = true;
                }

            } catch (Exception e) {
                Log.e(TAG, e.toString());
                isMember = false;
            }
        } else {
            Util.showToast(this, R.string.msg_NoNetwork);
        }
        return isMember;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isMemberTask != null) {
            isMemberTask.cancel(true);
        }
    }
}
