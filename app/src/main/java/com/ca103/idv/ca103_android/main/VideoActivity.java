package com.ca103.idv.ca103_android.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ca103.idv.ca103_android.R;


public class VideoActivity extends AppCompatActivity {
    String url = Util.URL + "/CourAndroidServlet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videopage);

        Bundle bundle = getIntent().getExtras();
        String cour_unit_id = bundle.getString("cour_unit_id");

        VideoView vv = findViewById(R.id.videoview);
        MediaController controller = new MediaController(this);
        vv.setMediaController(controller);
        vv.setVideoPath( url+"?cour_unit_id=" + cour_unit_id);
        vv.start();
    }
}
