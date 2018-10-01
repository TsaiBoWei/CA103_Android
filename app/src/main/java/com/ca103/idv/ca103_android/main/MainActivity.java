package com.ca103.idv.ca103_android.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.event.EventFragment;
import com.ca103.idv.ca103_android.member.LoginActivity;
import com.ca103.idv.ca103_android.member.ProfileFragment;
import com.ca103.idv.ca103_android.plan.PlanFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar();
        initDrawer();
        initBody();
        if ( !Util.networkConnected(this) )
            Util.showToast(this, R.string.msg_NoNetwork);
        else {
            Intent logintIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(logintIntent, Util.REQ_LOGIN);
        }
    }

    // 初始化Action Bar
    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if ( actionBar != null )
            // 讓drawer開啟時, 左上角出現 <- 圖示
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // 設定drawer關閉行為
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // 讓Drawer關閉時，出現三條線 |||
        actionBarDrawerToggle.syncState();
    }

    // 設定drawer選單行為
    private void initDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this,drawerLayout,
                        R.string.text_Open, R.string.text_Close);

        navigationView = findViewById(R.id.navigation_view);
        Fragment fragment;
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // 讓點選的menu產生選定的效果
                menuItem.setChecked(true);
                // 沒呼叫closeDrawers()的話，drawer不會自行退出
                drawerLayout.closeDrawers();
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.left_nav_home:
                        fragment = new HomeFragment();
                        switchFragment(fragment);
                        setTitle("首頁");
                        break;
                    case R.id.left_nav_profile:
                        fragment = new ProfileFragment();
                        bundle = new Bundle();

                        bundle.putString("mem_id", Util.memVO.getMem_id());
                        fragment.setArguments(bundle);
                        switchFragment(fragment);
                        setTitle("個人頁");
                        break;
                    case R.id.left_nav_myevent:
                        fragment = new EventFragment();
                        switchFragment(fragment);
                        setTitle("活動");
                        break;
                    case  R.id.left_nav_myplan:
                        fragment = new PlanFragment();
                        switchFragment(fragment);
                        setTitle("計畫");
                        break;

                    case R.id.left_nav_course:
                        setTitle("課程");
                        break;

                    case R.id.logout:
                        logout();
                        break;
                    default:
                        initBody();
                        break;
                }

                Util.fragment = fragment;
                return true;
            }
        });
    }

    private void initBody() {
        Fragment fragment = new HomeFragment();
        switchFragment(fragment);
        setTitle(R.string.text_Home);
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("登出確認")
                .setMessage("你確定要登出嗎?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Util.memVO = null;
                        SharedPreferences preferences = getSharedPreferences(Util.PREF_FILE,
                                MODE_PRIVATE);
                        // 設定外部空間參考 login
                        preferences.edit().putBoolean("login", false).apply();

                        Util.memVO = null;
                        Intent logintIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(logintIntent, Util.REQ_LOGIN);
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    // 登入事件回傳
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

    // 左上側邊欄按鈕被點擊時
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // to switch fragment, we need a fragmentManager to switch between
    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, fragment);
        fragmentTransaction.commit();
    }


    // 改寫返回鍵事件, 實作單一檢視後回到上一個視窗, 且專注在上一個選擇項
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return false;// return false 或者return true 都不会走onBackPressed了
        return false;
    }

}
