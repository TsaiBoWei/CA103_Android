package com.ca103.idv.ca103_android.member;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.ca103.idv.ca103_android.post.PostVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ProfileFragment extends Fragment {
    private static final String TAG = "/post/PostAndroid.do";
    private String mem_id;
    CommonTask postsTask;
    Gson gson;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewProfilePosts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        // Use Bundle to send data to fragment
        // not tried yet
        try {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "get_list_by_mem_id");
            jsonObject.addProperty("mem_id", "M000001");
            String jsonout = jsonObject.toString();
            Util.showToast(this.getActivity(),"doing post task");
            postsTask = new CommonTask(Util.URL+TAG, jsonout);
            String result = postsTask.execute().get();
            Type listType = new TypeToken<List<PostVO>>(){}.getType();
            List<PostVO> posts = gson.fromJson(result, listType);
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            posts.add(posts.get(0));
            Util.showToast(this.getActivity(),posts.get(0).getMem_id());
            recyclerView.setAdapter( new ProfileAdapter(posts) );
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return view;
    }


    private class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

        private List<PostVO> postList;

        public ProfileAdapter( List<PostVO> postlist) {
            this.postList = postlist;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivPhoto;
            private TextView tvCardContent;
            private TextView tvName;
            private Button btnCollect;
            private Button btnComment;
            public ViewHolder(View view){
                super(view);
                ivPhoto = view.findViewById(R.id.ivPhoto);
                ivPhoto.setImageResource(R.drawable.doge);
                tvCardContent = view.findViewById(R.id.tvCardContent);
                btnCollect = view.findViewById(R.id.btnCollect);
                btnCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                btnComment = view.findViewById(R.id.btnComment);
                btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                tvName = view.findViewById(R.id.tvName);
            }
        }

        @Override
        public int getItemCount() {
            return postList.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_post, viewGroup, false);
            return  new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final PostVO post = postList.get(position);
            holder.tvName.setText(post.getMem_id());
            holder.tvCardContent.setText(post.getPost_con());
            // itemView為ViewHolder內建屬性(指的就是每一列)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.showToast(getActivity(),post.getMem_id());
                }
            });

        }
    }
}
