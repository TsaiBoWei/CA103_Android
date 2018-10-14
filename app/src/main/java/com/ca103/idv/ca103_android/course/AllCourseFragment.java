package com.ca103.idv.ca103_android.course;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.event.EventWebVewActivity;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.ca103.idv.ca103_android.main.task.ImageTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AllCourseFragment extends Fragment {
    String url = Util.URL + "/CourAndroidServlet";
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

        getList();

        CourAdapter courAdapter = new CourAdapter(courlists);
        recyclerviewCour.setAdapter(courAdapter);

        return view;
    }

    public void getList() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "get_all_cour");
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

    public boolean buyCour(String cour_id, String mem_id) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "buy_cour");
        jsonObject.addProperty("cour_id", cour_id);
        jsonObject.addProperty("mem_id", mem_id);
        String jsonOut = jsonObject.toString();
        courseTask = new CommonTask(url, jsonOut);
        boolean result = false;

        try {
            String jsonin = courseTask.execute().get();
            if ( !"".equals(jsonin) ) {
                return true;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return result;
    }


    public class CourAdapter extends RecyclerView.Adapter<CourAdapter.ViewHolder> {

        List<CourlistVO> mCourlist;

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView ivCourLogo;
            private TextView tvCourPrice;
            private TextView tvCourName;
            private Button btnBuy;
            //private WebView wvEventContent;

            public ViewHolder(View itemView) {
                super(itemView);
                ivCourLogo = itemView.findViewById(R.id.ivCourLogo);
                tvCourPrice = itemView.findViewById((R.id.tvCourPrice));
                tvCourName = itemView.findViewById(R.id.tvCourName);
                btnBuy = itemView.findViewById(R.id.btnBuy);
            }
        }

        public CourAdapter(List<CourlistVO> list) {
            this.mCourlist = list;
        }

        @Override
        public CourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_course, viewGroup, false);
            return  new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final CourlistVO courlistVO = mCourlist.get(position);
            holder.tvCourPrice.setText(courlistVO.getCour_cost().toString());
            holder.tvCourName.setText(courlistVO.getCname());


            // 設定活動圖片
            // 須改用ImageTask
            int imageSize = getResources().getDisplayMetrics().widthPixels / 4;
            ImageTask evePhotoTask = new ImageTask(url, courlistVO.getCour_id(),
                    imageSize, holder.ivCourLogo);
            evePhotoTask.execute();

            holder.ivCourLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", courlistVO.getCour_text());
                    Intent intent = new Intent(getActivity(), EventWebVewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

            holder.btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( !buyCour(courlistVO.getCour_id(), Util.memVO.getMem_id() ))  {
                        new android.support.v7.app.AlertDialog.Builder(getContext())
                                .setTitle("購買失敗")
                                .setMessage("你已購買此課程!")
                                .setPositiveButton("我了解了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                    }
                    else {
                        new android.support.v7.app.AlertDialog.Builder(getContext())
                            .setTitle("購買成功!")
                            .setMessage("謝謝您的惠顧")
                            .setPositiveButton("不用謝我", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mCourlist.size();
        }

    }

}
