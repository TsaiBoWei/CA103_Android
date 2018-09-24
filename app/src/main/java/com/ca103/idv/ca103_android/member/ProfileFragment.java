package com.ca103.idv.ca103_android.member;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.ca103.idv.ca103_android.main.task.PostsTask;
import com.ca103.idv.ca103_android.post.PostVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private String mem_id;
    Gson gson;
    PostsTask postsTask;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewProfilePosts);

        // Use Bundle to send data to fragment
        // not tried yet
        postsTask = new PostsTask("M000001");
        try {
            List<PostVO> posts = postsTask.execute().get();
            recyclerView.setAdapter( new ProfileAdapter(inflater, posts) );
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return view;
    }


    private class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

        private LayoutInflater inflater;
        private List<PostVO> postList;
        private View visibleView;

        public ProfileAdapter(LayoutInflater inflater, List<PostVO> postlist) {
            this.inflater = inflater;
            this.postList = postlist;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageButton ibtnPhoto;
            private TextView tvCardContent;
            private Button btnCollect;
            private Button btnComment;
            public ViewHolder(View view){
                super(view);
                ibtnPhoto = view.findViewById(R.id.ibtnPhoto);
                tvCardContent = view.findViewById(R.id.tvCardContent);
                btnCollect = view.findViewById(R.id.btnCollect);
                btnComment = view.findViewById(R.id.btnComment);
            }
        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = inflater.inflate(R.layout.cardview_post, viewGroup, false);
            return  new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final PostVO post = postList.get(position);
            holder.tvCardContent.setText(post.getPost_con());
        }
    }
}
