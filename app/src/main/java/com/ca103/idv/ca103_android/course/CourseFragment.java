package com.ca103.idv.ca103_android.course;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CourseFragment extends android.support.v4.app.Fragment{
    String url = Util.URL + "/CourAndroidServlet";
    public static final String TAG = "CourseFragment";
    RecyclerView recyclerviewCour;
    List<CourlistVO> courlists;
    Gson gson;
    CommonTask courseTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        recyclerviewCour = view.findViewById(R.id.recyclerviewCour);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerviewCour.setLayoutManager(layoutManager);

        // 取得已購買清單
        getList();

        CourAdapter courAdapter = new CourAdapter(courlists);
        recyclerviewCour.setAdapter(courAdapter);

        return view;
    }

    public void getList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "get_purch_cour_by_mem_id");
        jsonObject.addProperty("mem_id", Util.memVO.getMem_id());
        String jsonOut = jsonObject.toString();
        courseTask = new CommonTask(url, jsonOut);

        Type listtype = new TypeToken<List<CourlistVO>>(){}.getType();
        try{
            String jsonin = courseTask.execute().get();
            courlists = gson.fromJson(jsonin, listtype);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public class CourAdapter extends RecyclerView.Adapter<CourAdapter.ViewHolder> {

        List<CourlistVO> mCourlist;

        public CourAdapter(List<CourlistVO> list) {
            this.mCourlist = list;
        }

        @Override
        public CourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_plan, viewGroup, false);
            return  new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final CourlistVO courlistVO = mCourlist.get(position);
            holder.tvCName.setText(courlistVO.getCname());
            holder.tvCost.setText(courlistVO.getCour_cost().toString());
            holder.cardview_cour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SingleCourlistActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cour_id", courlistVO.getCour_id());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCourlist.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private CardView cardview_cour;
            private TextView tvCost;
            private TextView tvCName;

            private ViewHolder(View itemView) {
                super(itemView);
                tvCost = itemView.findViewById((R.id.tvPlanStartDate));
                tvCName = itemView.findViewById(R.id.tvPlanTitle);
                cardview_cour = itemView.findViewById(R.id.cardview_plan);
            }
        }
    }



}
