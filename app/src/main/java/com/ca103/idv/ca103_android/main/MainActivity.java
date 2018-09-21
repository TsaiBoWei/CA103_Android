package com.ca103.idv.ca103_android.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.member.LoginActivity;

import static com.ca103.idv.ca103_android.main.Util.PAGES;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent logintIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(logintIntent, Util.REQ_LOGIN);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK ) {
            switch(requestCode) {
                case Util.REQ_LOGIN :
                    break;
            }
        }
    }
    private class MyGridViewAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;

        public MyGridViewAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public Object getItem(int position) {
            return PAGES[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = layoutInflater.inflate(R.layout.gridview_main, parent, false);
//            }
//            Page page = PAGES[position];
//            ImageView ivCategory = convertView.findViewById(R.id.ivLogo);
//            ivCategory.setImageResource(page.getImage());
//            TextView tvTitle = convertView.findViewById(R.id.tvTitle);
//            tvTitle.setText(page.getTitle());
            return null;
        }
    }



}
