package com.ca103.idv.ca103_android.plan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;


import com.ca103.idv.ca103_android.R;

import com.ca103.idv.ca103_android.event.EventVO;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment {
    String url = Util.URL + "/PlanAndroidServlet.do";
    CommonTask planTask;
    CalendarView calenderView;
    RecyclerView recyclerView;
    List<PlanVO> planlist;
    List<EventVO> eventlist;

    Gson gson ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        // 設定Gson日期物件
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        getLists();
        List<ThingsVO> stuffs = new ArrayList<ThingsVO>();

        recyclerView = view.findViewById(R.id.recyclerViewPlan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        // 設定calenderView點擊事件
        calenderView = view.findViewById(R.id.calendarView);
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Util.showToast(view.getContext(), "month" + month + "day" + dayOfMonth);
                // 雖然是已廢棄的方法
                // 但是可以用就好了
                Timestamp date = new java.sql.Timestamp(year,month,dayOfMonth,0,0,0,0);

                for ( PlanVO pvo : planlist ) {
                    if ( pvo.getPlan_start_date().before(date) && pvo.getPlan_end_date().after(date) ) {
                    }

                }

                //planlist.get(0).getPlan_start_date();

            }
        });


        //recyclerView.setAdapter( new PlanAdapter(stuffs) );
        return view;
    }

    // 依據會員ID取得其所有活動, 計畫, 課程
    // 僅接收文字訊息的話, 檔案不會太大

    public void getLists() {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "get_plan_by_mem_id");
        jsonObject.addProperty("mem_id", Util.memVO.getMem_id());
        String jsonOut = jsonObject.toString();
        planTask = new CommonTask(url, jsonOut);

        Type listType = new TypeToken<List<PlanVO>>(){}.getType();
        try {
            String jsonIn = planTask.execute().get();
            planlist = gson.fromJson(jsonIn, listType);
        } catch ( Exception e ){
            e.printStackTrace();
        }

        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "get_eve_by_mem_id");
        jsonObject.addProperty("mem_id", Util.memVO.getMem_id());
        jsonOut = jsonObject.toString();
        planTask = new CommonTask(url, jsonOut);

        listType = new TypeToken<List<EventVO>>(){}.getType();
        try {
            String jsonIn = planTask.execute().get();
            eventlist = gson.fromJson(jsonIn, listType);
        } catch ( Exception e ){
            e.printStackTrace();
        }

    }

    public class ThingsVO {
        String title;
        Timestamp start_date;
        Timestamp end_date;
        String content;

        public ThingsVO( String title, Timestamp start, Timestamp end, String content ){
            this.title=title;
            this.start_date = start;
            this.end_date = end;
            this.content = content;
        }

    }


    // 建立一個查詢程式, 當使用者改變選擇日期時以日期為條件, 對活動, 計畫, 課程做查詢
    // 如果選擇日期為活動期間內, 則將其加入RecyclerView之內
    // 還要有card的點擊事件, 透過action轉出該活動/計畫/課程之內容
    private class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

        public List<EventVO> eventList;

        public PlanAdapter(List<EventVO> list){ this.eventList = list; }

        // 單一card內的viewholder
        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView ivEventLogo;
            private TextView tvEventStartDate;
            private TextView tvEventTitle;
            //private WebView wvEventContent;

            public ViewHolder(View itemView) {
                super(itemView);
                ivEventLogo = itemView.findViewById(R.id.ivEventLogo);
                tvEventStartDate = itemView.findViewById((R.id.tvEventStartDate));
                tvEventTitle = itemView.findViewById(R.id.tvEventTitle);

            }
        }

        @NonNull
        @Override
        public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event, parent,false);
            return new PlanAdapter.ViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull final PlanAdapter.ViewHolder holder, int position) {
            final EventVO eventVO = eventList.get(position);
            holder.tvEventTitle.setText(eventVO.getEve_title());
            holder.tvEventStartDate.setText(eventVO.getEve_startdate().toString());

            // 設定活動圖片
            // 須改用ImageTask
            if ( eventVO.getEve_photo() != null ) {
                Bitmap decode64 =
                        BitmapFactory.decodeByteArray(eventVO.getEve_photo(),
                                0,
                                eventVO.getEve_photo().length);
                holder.ivEventLogo.setImageBitmap(decode64);
            }

            holder.ivEventLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", eventVO.getEve_content());
                    Intent intent = new Intent(getActivity(), com.ca103.idv.ca103_android.event.EventWebVewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }
    }

}
