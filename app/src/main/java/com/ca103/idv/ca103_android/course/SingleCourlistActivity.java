package com.ca103.idv.ca103_android.course;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.VideoActivity;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.ca103.idv.ca103_android.plan.PlanFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SingleCourlistActivity extends Activity {
    String url = Util.URL + "/CourAndroidServlet";
    List<CourunitVO> courunits;
    RecyclerView recyclerView;
    CommonTask courseTask;
    Gson gson;
    String cour_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        setContentView(R.layout.activity_singlecour);
        recyclerView = findViewById(R.id.recyclerViewSingleCour);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Bundle bundle = getIntent().getExtras();
        cour_id = bundle.getString("cour_id");

        getList();
        SingleCourlistAdapter singleCourlistAdapter = new SingleCourlistAdapter(courunits);
        recyclerView.setAdapter(singleCourlistAdapter);
    }
    public void getList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "get_cour_units_by_cour_id");
        jsonObject.addProperty("cour_id", cour_id );
        String jsonOut = jsonObject.toString();
        courseTask = new CommonTask(url, jsonOut);

        Type listtype = new TypeToken<List<CourunitVO>>(){}.getType();
        try{
            String jsonin = courseTask.execute().get();
            courunits = gson.fromJson(jsonin, listtype);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class SingleCourlistAdapter extends RecyclerView.Adapter<SingleCourlistAdapter.ViewHolder>{

        private List<CourunitVO> mcourunitList;

        public SingleCourlistAdapter( List<CourunitVO> list ) {this.mcourunitList = list;}

        @NonNull
        @Override
        public SingleCourlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_singlecourvideo, parent,false);
            return new SingleCourlistAdapter.ViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull final SingleCourlistAdapter.ViewHolder holder, int position) {
            final CourunitVO courunitVO = mcourunitList.get(position);
            holder.tvCourunitTitle.setText(courunitVO.getCu_name());
            holder.btnStartVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SingleCourlistActivity.this, VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cour_unit_id", courunitVO.getCour_unit_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mcourunitList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvCourunitTitle;
            private Button btnStartVideo;

            private ViewHolder(View itemView) {
                super(itemView);
                tvCourunitTitle = itemView.findViewById(R.id.tvCourunitTitle);
                btnStartVideo = itemView.findViewById(R.id.btnStartVideo);
            }
        }

    }
}
