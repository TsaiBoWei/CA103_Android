package com.ca103.idv.ca103_android.member;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.main.Util;
import com.ca103.idv.ca103_android.main.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

public class SigninActivity extends AppCompatActivity {
    private static final String TAG = "SigninActivity";
    CommonTask signinTask;

    Gson gson;
    TextView tvName;
    TextView tvAccount;
    TextView tvPassword;
    TextView tvEmail;
    TextView tvErrormsgSignin;
    Button btnSubmit;
    Button btnCancel;
    Button btnUpload;

    ImageView ivUploadUserPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        setResult(RESULT_CANCELED);
        initBody();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initBody() {
        tvName = findViewById(R.id.tvName);
        tvAccount = findViewById(R.id.tvAccount);
        tvPassword = findViewById(R.id.tvPassword);
        tvErrormsgSignin = findViewById(R.id.tvErrormsgSignin);
        tvEmail = findViewById(R.id.tvEmail);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        btnUpload = findViewById(R.id.btnUpload);
        ivUploadUserPic = findViewById(R.id.ivUploadUserPic);
    }

    public void submitOnclick( View view ) { // signin
        if ( checkInput() ) {
            MemVO memVO = new MemVO();
            memVO.setMem_name(tvName.getText().toString());
            memVO.setMem_account(tvAccount.getText().toString());
            memVO.setMem_password(tvPassword.getText().toString());
            memVO.setMem_email(tvEmail.getText().toString());
            Bitmap bitmap = ((BitmapDrawable) ivUploadUserPic.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            memVO.setMem_photo(baos.toByteArray());

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "signin");
            jsonObject.addProperty("memVO", gson.toJson(memVO, MemVO.class));

            String jsonOut = jsonObject.toString();

            try {
                String url = Util.URL + "/mem/memAndroid.do";
                signinTask = new CommonTask(url, jsonOut);
                signinTask.execute();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
        setResult(RESULT_OK);
        finish();
    }

    public void cancelOnclick(View view ) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void uploadOnclick(View view ) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Util.PICK_PHOTO_FOR_AVATAR);
    }

    private boolean checkInput() {
        boolean result = false;
        StringBuilder errmsg = new StringBuilder(tvErrormsgSignin.getText().toString()) ;

        if ( tvName.getText().equals("") ) {
            errmsg.append( "User name can't be null." + "\\n" );
        }

        if ( tvAccount.getText().equals("") ) {
            errmsg.append( "Account can't be null." + "\\n" );
        }

        if ( tvPassword.getText().equals("") ) {
            errmsg.append( "Password can't be null." + "\\n" );
        }

        if ( errmsg.toString().length() == 0 )
            result = true;

        tvErrormsgSignin.setText(errmsg);
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Util.PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                final Bundle extras = data.getExtras();
                Bitmap newProfilePic = extras.getParcelable("data");
                ivUploadUserPic.setImageBitmap(newProfilePic);
            } catch (NullPointerException ne) {
                StringBuilder errmsg = new StringBuilder(tvErrormsgSignin.getText().toString()) ;
                errmsg.append("upoload failed, null pointer exception");
                tvErrormsgSignin.setText(errmsg);
                Log.e(TAG, ne.toString());
            }
            catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }

}
