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
import com.ca103.idv.ca103_android.course.AllCourseFragment;
import com.ca103.idv.ca103_android.event.EventFragment;

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

        TabLayout tabLayout = view.findViewById(R.id.tablayoutMyCour);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
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
            Bundle bundle = new Bundle();
            bundle.putString("type", "hot");

            EventFragment eventFragment = new EventFragment();
            eventFragment.setArguments(bundle);
            pageList.add(new Page(eventFragment, "熱門活動"));

            AllCourseFragment courseFragment = new AllCourseFragment();
            courseFragment.setArguments(bundle);
            pageList.add( new Page(courseFragment, "熱門課程"));

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
