package com.ca103.idv.ca103_android.plan;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;


import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.course.CourlistVO;
import com.ca103.idv.ca103_android.event.EventVO;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.member.MemVO;
import com.ca103.idv.ca103_android.post.PostVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment {

    CalendarView calenderView;
    RecyclerView recyclerView;
    List<PlanVO> planlist;
    List<CourlistVO> courlist;
    List<EventVO> eventlist;

    Gson gson ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_plan, container, false);
        // 設定Gson日期物件
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        // 設定calenderView點擊事件
        calenderView = view.findViewById(R.id.calendarView);
        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Util.showToast(view.getContext(), "month" + month + "day" + dayOfMonth);
            }
        });

        recyclerView = view.findViewById(R.id.recyclerViewPlan);
        return view;
    }

    // 依據會員ID取得其所有活動, 計畫, 課程
    // 僅接收文字訊息的話, 檔案不會太大

    public void getLists() {

    }


    // 建立一個查詢程式, 當使用者改變選擇日期時以日期為條件, 對活動, 計畫, 課程做查詢
    // 如果選擇日期為活動期間內, 則將其加入RecyclerView之內
    // 還要有card的點擊事件, 透過action轉出該活動/計畫/課程之內容


}
