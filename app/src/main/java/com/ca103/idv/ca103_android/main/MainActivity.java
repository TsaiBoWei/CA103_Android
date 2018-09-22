package com.ca103.idv.ca103_android.main;


import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import com.ca103.idv.ca103_android.R;
import com.ca103.idv.ca103_android.member.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionBar();
        initDrawer();
        initBody();
        Intent logintIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(logintIntent, Util.REQ_LOGIN);
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
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // 讓點選的menu產生選定的效果
                menuItem.setChecked(true);
                // 沒呼叫closeDrawers()的話，drawer不會自行退出
                drawerLayout.closeDrawers();
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.item_Home:
                        initBody();
                        break;
                    default:
                        initBody();
                        break;
                }
                return true;
            }
        });
    }

    private void initBody() {
        Fragment fragment = new HomeFragment();
        switchFragment(fragment);
        setTitle(R.string.text_Home);
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



}
