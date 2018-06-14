package com.mab.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mab.chatroom.Data.ChatRoomAPI;
import com.mab.chatroom.Data.ChatsController;
import com.mab.chatroom.Data.SendMessageController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.Chat;
import com.mab.chatroom.Model.ChatAdapter;
import com.mab.chatroom.Model.ChatResponse;
import com.mab.chatroom.Model.RoomId;
import com.mab.chatroom.Model.SendMessage;
import com.mab.chatroom.Model.SendMessageResponse;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView message;
    private TextView noMessage;
    private ImageView sendIcon;
    private ProgressBar progressBar;


    private ChatAdapter chatAdapter;
    private List<Chat> chats = new ArrayList<>();
    private RoomId roomId;

    private String accessToken = "bearer " + UserPreferenceManager.getInstance(this).getAccessToken();
    private String username = UserPreferenceManager.getInstance(this).getUsername();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        findViews();
        initChatList();
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        Gson gson = new Gson();
        roomId = gson.fromJson(intent.getStringExtra("roomId"),RoomId.class);

        ChatRoomAPI.getChatsCallBack getChatsCallBack = new ChatRoomAPI.getChatsCallBack() {
            @Override
            public void onResponse(ChatResponse chatResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                if (chatResponse.getChats().isEmpty()){
                    noMessage.setVisibility(View.VISIBLE);
                    noMessage.setText(R.string.no_message);
                }
                else {
                    noMessage.setVisibility(View.INVISIBLE);
                    chats.clear();
                    chats.addAll(chatResponse.getChats());
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chats.size() - 1);
                }
            }

            @Override
            public void onFailure(String cause) {
                Toast.makeText(getApplicationContext(),cause,Toast.LENGTH_LONG).show();
            }
        };
        ChatsController chatsController = new ChatsController(getChatsCallBack);
        chatsController.start(accessToken, roomId);


        sendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText() == null) {
                }
                else {

                    noMessage.setVisibility(View.INVISIBLE);
                    Chat chat = new Chat();
                    chat.setUsername(username);
                    chat.setText(message.getText().toString());
                    chats.add(chat);
                    chatAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chats.size() - 1);

                    ChatRoomAPI.sendMessageCallBack sendMessageCallBack = new ChatRoomAPI.sendMessageCallBack() {
                        @Override
                        public void onResponse(SendMessageResponse sendMessageResponse) {
                            message.setText("");
                        }

                        @Override
                        public void onFailure(String cause) {

                        }
                    };
                    SendMessage sendMessage = new SendMessage(roomId.getId(), message.getText().toString(),username);
                    SendMessageController sendMessageController = new SendMessageController(sendMessageCallBack);
                    sendMessageController.start(accessToken, sendMessage);
                }
            }
        });
    }

    private void initChatList(){
        chatAdapter = new ChatAdapter(this,chats,username);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatsActivity.this));
        recyclerView.setAdapter(chatAdapter);
    }

    private void findViews(){
        recyclerView = findViewById(R.id.chats_list);
        message = findViewById(R.id.input_message);
        sendIcon = findViewById(R.id.send_icon);
        noMessage = findViewById(R.id.no_message);
        progressBar = findViewById(R.id.progress_bar);
    }
}
