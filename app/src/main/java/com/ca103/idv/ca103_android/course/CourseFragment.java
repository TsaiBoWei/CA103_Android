package com.ca103.idv.ca103_android.course;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.member.ProfileFragment;

import java.util.List;

public class CourseFragment extends android.support.v4.app.Fragment{

    RecyclerView recyclerviewCour;
    TabLayout tabLayout;
    List<CourlistVO> cour;
    List<CourunitVO> courUnitList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerviewCour.setLayoutManager(layoutManager);
        findViews(view);
        return view;
    }

    public void findViews( View view ) {
        recyclerviewCour = view.findViewById(R.id.recyclerviewCour);

        tabLayout = view.findViewById(R.id.tablayoutMyCour);
    }

    public class CourAdapter extends RecyclerView.Adapter<CourAdapter.ViewHolder> {

        List<CourlistVO> mCourlist;
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_plan, viewGroup, false);
            return  new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
            }
        }
    }



}
