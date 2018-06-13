package com.mab.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mab.chatroom.Data.ChatRoomAPI;
import com.mab.chatroom.Data.CreateRoomController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.CreateRoomResponse;
import com.mab.chatroom.Model.RoomName;

public class CreateRoomActivity extends AppCompatActivity {
    private EditText roomName;
    private Button create;
    private String accessToken = "bearer " + UserPreferenceManager.getInstance(this).getAccessToken();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_room);
        findViews();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChatRoomAPI.createRoomCallBack createRoomCallBack = new ChatRoomAPI.createRoomCallBack() {
                    @Override
                    public void onResponse(CreateRoomResponse createRoomResponse) {
                        Toast.makeText(getApplicationContext(), R.string.creat_room_successful,Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CreateRoomActivity.this,MainActivity.class);
                        CreateRoomActivity.this.startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String cause) {

                    }
                };

                CreateRoomController createRoomController = new CreateRoomController(createRoomCallBack);
                createRoomController.start(accessToken,new RoomName(roomName.getText().toString()));

            }
        });
    }

    private void findViews(){
        roomName = findViewById(R.id.room_name);
        create = findViewById(R.id.create);
    }
}
