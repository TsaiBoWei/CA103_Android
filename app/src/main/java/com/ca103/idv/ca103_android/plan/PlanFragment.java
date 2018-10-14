package com.ca103.idv.ca103_android.plan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanFragment extends Fragment {
    String url = Util.URL + "/PlanAndroidServlet";
    CommonTask planTask;
    CalendarView calenderView;
    RecyclerView recyclerView;
    List<PlanVO> planlist;
    List<EventVO> eventlist;
    List<ThingsVO> stuffs;

    PlanAdapter planAdapter;
    Gson gson ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        // 設定Gson日期物件
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        planlist = new ArrayList<>();
        eventlist = new ArrayList<>();

        getLists();
        stuffs = new ArrayList<ThingsVO>();

        recyclerView = view.findViewById(R.id.recyclerViewPlan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        setByDay( System.currentTimeMillis() );
        planAdapter = new PlanAdapter(stuffs);
        recyclerView.setAdapter( planAdapter );


        // 設定calenderView點擊事件
        calenderView = view.findViewById(R.id.calendarView);
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat smd;
                smd = new SimpleDateFormat("yyyy-MM-dd");
                String input = year + "-" + ++month + "-" + dayOfMonth;
                Date t = new Date(System.currentTimeMillis());
                try {
                    t = smd.parse(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                long get_clicked_dat = t.getTime();
                stuffs.clear();
                setByDay(get_clicked_dat);
                planAdapter.notifyDataSetChanged();
            }
        });
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

    public void setByDay(long input_time ) {

        Timestamp date = new Timestamp(input_time);

        List<ThingsVO> new_stuffs = new ArrayList<>();
        DateFormat dayformat = DateFormat.getDateInstance();

        String get_day = dayformat.format(input_time);

        if ( planlist != null ) {
            for (PlanVO pvo : planlist) {
                // 如果開始日在今天之前或正好今天, 且 結束日在今天之後或正好今天
                if ((pvo.getPlan_start_date().before(date) ||
                        get_day.equals(dayformat.format(pvo.getPlan_start_date())))
                        && (pvo.getPlan_end_date().after(date) ||
                        get_day.equals(dayformat.format(pvo.getPlan_end_date())))) {

                    new_stuffs.add(new ThingsVO(pvo.getPlan_name(), "plan", pvo.getPlan_id(), pvo.getPlan_start_date(),
                            pvo.getPlan_end_date()));
                }
            }
        }

        if ( eventlist != null ) {
            for (EventVO evo : eventlist) {
                if ((evo.getEve_startdate().before(date) ||
                        get_day.equals(dayformat.format(evo.getEve_startdate())))
                        && (evo.getEve_enddate().after(date) ||
                        get_day.equals(dayformat.format(evo.getEve_enddate())))) {

                    new_stuffs.add(new ThingsVO(evo.getEve_title(), "event", evo.getEve_id(), evo.getEve_startdate(),
                            evo.getEve_enddate()));
                }
            }
        }
        stuffs.addAll(new_stuffs);
    }

    public class ThingsVO {
        String title;
        String type;
        String id;
        Timestamp start_date;
        Timestamp end_date;

        private ThingsVO(String title, String type, String id, Timestamp start, Timestamp end){
            this.title=title;
            this.type = type;
            this.id = id;
            this.start_date = start;
            this.end_date = end;
        }

    }


    // 建立一個查詢程式, 當使用者改變選擇日期時以日期為條件, 對活動, 計畫, 課程做查詢
    // 如果選擇日期為活動期間內, 則將其加入RecyclerView之內
    // 還要有card的點擊事件, 透過action轉出該活動/計畫/課程之內容
    private class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

        private List<ThingsVO> mStuffs;

        private PlanAdapter(List<ThingsVO> list) {
            this.mStuffs = list;
        }

        @NonNull
        @Override
        public PlanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_plan, parent,false);
            return new PlanAdapter.ViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull final PlanAdapter.ViewHolder holder, int position) {
            final ThingsVO thing = mStuffs.get(position);
            holder.tvPlanTitle.setText(thing.title);
            holder.tvPlanStartDate.setText(thing.start_date.toString());

            if ( "plan".equals(thing.type) ) {
                holder.cardview_plan.setCardBackgroundColor(getResources().getColor(R.color.card_planpage_plan_color));
            } else if ("event".equals(thing.type) ) {
                holder.cardview_plan.setCardBackgroundColor(getResources().getColor(R.color.card_planpage_event_color));
            }

        }

        @Override
        public int getItemCount() {
            return mStuffs.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private CardView cardview_plan;
            private TextView tvPlanStartDate;
            private TextView tvPlanTitle;

            private ViewHolder(View itemView) {
                super(itemView);
                tvPlanStartDate = itemView.findViewById((R.id.tvPlanStartDate));
                tvPlanTitle = itemView.findViewById(R.id.tvCourName);
                cardview_plan = itemView.findViewById(R.id.cardview_plan);
            }
        }
    }



}
