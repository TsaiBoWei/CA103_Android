package com.ca103.idv.ca103_android.member;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.List;

public class ProfileFragment extends Fragment {
    private static final String TAG = "/post/PostAndroid.do";
    private String mem_id;

    CommonTask postsTask;
    Bundle bundle;
    Gson gson;

    ImageView ivProfilePicture;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // recycler view part
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewProfilePosts);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // create gson
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        // Use Bundle to get data from activity
        bundle = this.getArguments();
        if ( !bundle.isEmpty() )
            mem_id = bundle.getString("mem_id");

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        if ( Util.memVO.getMem_photo() != null  ) {
            Bitmap decode64 =
                    BitmapFactory.decodeByteArray(Util.memVO.getMem_photo(),
                            0,
                            Util.memVO.getMem_photo().length);
            ivProfilePicture.setImageBitmap(decode64);
        }
        else {
            ivProfilePicture.setImageResource(R.drawable.doge);
            Bitmap decode = BitmapFactory.decodeResource(getResources(),R.drawable.doge);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            decode.compress(Bitmap.CompressFormat.PNG, 0, blob);
            byte[] bitmapdata = blob.toByteArray();
            Util.memVO.setMem_photo(bitmapdata);
        }
        // get all posts
        try {
            // servlet request


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "get_list_by_mem_id");
            jsonObject.addProperty("mem_id", mem_id);
            String jsonout = jsonObject.toString();

            // create task
            postsTask = new CommonTask(Util.URL+TAG, jsonout);
            String result = postsTask.execute().get();
            Type listType = new TypeToken<List<PostVO>>(){}.getType();

            List<PostVO> posts = gson.fromJson(result, listType);
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
            private Button btnComment;
            public ViewHolder(View view){
                super(view);
                ivPhoto = view.findViewById(R.id.ivPhoto);

                MemVO memvo = Util.memVO;
                if ( memvo.getMem_photo() != null ) {
                    Bitmap decode64 =
                            BitmapFactory.decodeByteArray(Util.memVO.getMem_photo(),
                                    0,
                                    Util.memVO.getMem_photo().length);
                    ivPhoto = view.findViewById(R.id.ivPhoto);
                    ivPhoto.setImageBitmap(decode64);
                }

                tvCardContent = view.findViewById(R.id.tvCardContent);
                btnComment = view.findViewById(R.id.btnComment);
                btnComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // show
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


    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.commit();
    }
}
