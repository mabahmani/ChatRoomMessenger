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
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mab.chatroom.Data.RegisterUserController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.User;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
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
                CreateRoomDialog createRoomDialog = new CreateRoomDialog(MainActivity.this);
                createRoomDialog.show();
            }
        });

    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getFragmentManager().popBackStack();
            openMainFragment();
        }
    };

    private void openAuthFragment(){
        authFragmentPagerAdapter = new AuthFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(authFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void openMainFragment(){
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
        floatingActionButton = findViewById(R.id.new_chat_room_button);
    }
}
