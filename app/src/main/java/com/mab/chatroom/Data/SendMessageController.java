package com.mab.chatroom.Data;

import android.widget.Toast;

import com.mab.chatroom.Model.SendMessage;
import com.mab.chatroom.Model.SendMessageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendMessageController {
    private ChatRoomAPI.sendMessageCallBack sendMessageCallBack;

    public SendMessageController(ChatRoomAPI.sendMessageCallBack sendMessageCallBack) {
        this.sendMessageCallBack = sendMessageCallBack;
    }

    public void start(String accessToken, SendMessage sendMessage){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<SendMessageResponse> call = chatRoomAPI.sendMessage(accessToken,sendMessage);
        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                if (response.isSuccessful())
                    sendMessageCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<SendMessageResponse> call, Throwable t) {
                sendMessageCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
