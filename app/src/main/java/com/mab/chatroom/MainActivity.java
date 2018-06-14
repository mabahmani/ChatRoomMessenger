package com.mab.chatroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.User;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPager viewPager2;
    private FloatingActionButton floatingActionButton;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private AuthFragmentPagerAdapter authFragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        if(UserPreferenceManager.getInstance(this).getAccessToken() == null){
            openAuthFragment();
        }
        else {
            openMainFragment();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CreateRoomActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewPager.setVisibility(View.GONE);
            openMainFragment();
        }
    };

    private void openAuthFragment(){
        floatingActionButton.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
        authFragmentPagerAdapter = new AuthFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(authFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void openMainFragment(){
        floatingActionButton.setVisibility(View.VISIBLE);
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager2.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager2);
    }


    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                broadcastReceiver, new IntentFilter("login_successful")
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
    }

    private void findViews(){
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPager2 = findViewById(R.id.view_pager2);
        floatingActionButton = findViewById(R.id.new_chat_room_button);
    }
}
