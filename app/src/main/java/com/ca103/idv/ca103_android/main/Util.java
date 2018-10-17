package com.ca103.idv.ca103_android.main;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.ca103.idv.ca103_android.member.MemVO;


public class Util {
    // 模擬器連Tomcat
    // public static String URL = "http://10.0.2.2:8081/CA103G10908";
    //public static String URL = "http://172.20.10.6:8081/BookStoreWeb/";
    public static String URL = "http://192.168.1.104:8081/CA103G10908";
    // 偏好設定檔案名稱
    public final static String PREF_FILE = "preference";

    public static final int REQ_LOGIN = 1;
    public static final int REQ_SIGNIN = 2;
    public static final int PICK_PHOTO_FOR_AVATAR = 3;

    public static MemVO memVO = null;

    public static Fragment fragment = null;

    // check if the device connect to the network
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    public String removeHtmlTag(String input) {
        return input.replaceAll("\\<.*?\\>", "");
    }


    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
