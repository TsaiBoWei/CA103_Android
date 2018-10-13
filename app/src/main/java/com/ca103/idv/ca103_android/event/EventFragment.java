package com.ca103.idv.ca103_android.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.ca103.idv.ca103_android.main.task.ImageTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class EventFragment extends Fragment {
    private static final String TAG = "/eve/EveAndroid.do";
    private static final int ALL_EVENT = 1;
    private static final int MY_EVENT = 2;
    CommonTask eventtask;
    Gson gson;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEvent);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        setList(ALL_EVENT, recyclerView);

        return view;
    }

    // 在個人活動與全部活動中切換
    public void setList( int list_status, RecyclerView recyclerView ) {

        JsonObject jsonObject = new JsonObject();
        String jsonout = "";

        if ( list_status == ALL_EVENT ) {
            jsonObject.addProperty("action", "get_all_eve");
            jsonout = jsonObject.toString();
        }

        else if ( list_status == MY_EVENT ) {
            jsonObject.addProperty("action", "get_list_by_mem_id");
            jsonObject.addProperty("mem_id", Util.memVO.getMem_id());
            jsonout = jsonObject.toString();
        }

        eventtask = new CommonTask(Util.URL+TAG, jsonout);
        try {
            String result = eventtask.execute().get();
            Type listType = new TypeToken<List<EventVO>>(){}.getType();
            List<EventVO> list = gson.fromJson(result, listType);
            recyclerView.setAdapter(new EventAdapter(list));
        } catch (Exception e ) {
            Log.e(TAG, e.toString());
        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

        private List<EventVO> eventList;

        public EventAdapter(List<EventVO> list){ this.eventList = list; }

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
                tvEventTitle = itemView.findViewById(R.id.tvPlanTitle);
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event, parent,false);
            return new ViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            final EventVO eventVO = eventList.get(position);

            holder.tvEventTitle.setText(eventVO.getEve_title());
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            Date a;
            try {
                a = dateFormat.parse(dateFormat.format(eventVO.getEve_startdate()));

            }catch (Exception e) {
                e.printStackTrace();
            }


            holder.tvEventStartDate.setText(dateFormat.format(eventVO.getEve_startdate()));


            // 設定活動圖片
            // 須改用ImageTask
            int imageSize = getResources().getDisplayMetrics().widthPixels / 4;
            ImageTask evePhotoTask = new ImageTask(Util.URL+TAG, eventVO.getEve_id(),
                    imageSize, holder.ivEventLogo);
            evePhotoTask.execute();

            holder.ivEventLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("content", eventVO.getEve_content());
                    Intent intent = new Intent(getActivity(), EventWebVewActivity.class);
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
