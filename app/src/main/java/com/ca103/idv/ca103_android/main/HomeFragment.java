package com.ca103.idv.ca103_android.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca103.idv.ca103_android.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(new HomeFragmentAdapter(getActivity().getSupportFragmentManager()));
        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class HomeFragmentAdapter extends FragmentPagerAdapter {
        List<Page> pageList;

        public HomeFragmentAdapter(FragmentManager fm) {
            super(fm);
            pageList = new ArrayList<Page>(){};

            //pageList.add(new Page(new EventFragment(), "熱門活動"));
            // 要改為 PlanListFragment
            //pageList.add(new Page(new PlanFragment(), "熱門計畫"));
            // 缺一個stream fragment
            // pageList.add(new Page( new StreamFragment(), "直播頁面"));
        }
        @Override
        public Fragment getItem(int position) {
            return pageList.get(position).getFragment();
        }

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageList.get(position).getTitle();
        }

    }

}
