package com.ca103.idv.ca103_android.main.task;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.ca103.idv.ca103_android.post.PostVO;
import com.ca103.idv.ca103_android.main.Util;

public class PostsTask extends AsyncTask<String, Integer, List<PostVO>> {
    private static final String TAG = "PostsTask";
    private String mem_id;

    public PostsTask(String mem_id) {
        this.mem_id = mem_id;
    }


    @Override
    protected List<PostVO> doInBackground(String... str){
        String urlString = Util.URL + "PostAndroidServlet";
        DataOutputStream dos = null;
        HttpURLConnection connection = null;
        StringBuilder sb = null;
        try{
            URL url = new URL(urlString);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // 不知道請求內容大小時可以呼叫此方法將請求內容分段傳輸，設定0代表使用預設大小
            // 參考HttpURLConnection API的Posting Content部分

            connection.setChunkedStreamingMode(0);
            // not use cache
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");
            connection.connect();

            dos = new DataOutputStream(connection.getOutputStream());
            // 使用此方式則可以在servlet使用req.getParameter方法取得請求參數
            // (可跟web端servlet對接)
            String content = "action=get_list_by_mem_id";
            dos.writeBytes(content);
            content="mem_id=" + mem_id;
            dos.writeBytes(content);
            dos.flush();

            int statusCode = connection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK ) {
                sb = new StringBuilder();
                BufferedReader br =
                        new BufferedReader( new InputStreamReader(connection.getInputStream()));
                String strline;
                while ( (strline = br.readLine()) != null ) {
                    sb.append(strline);
                }
                br.close();
            }
            else {
                Log.e(TAG,"statusCode = " + statusCode );
            }
        } catch (Exception e){
            Log.e(TAG, e.toString());
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        } // finally

        if ( sb != null ) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Type listtype = new TypeToken<List<PostVO>>(){}.getType();
            return gson.fromJson(sb.toString(), listtype );
        }

        return null;
    }
}
