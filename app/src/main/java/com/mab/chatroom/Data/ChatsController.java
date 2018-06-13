package com.mab.chatroom.Data;

import com.mab.chatroom.Model.ChatResponse;
import com.mab.chatroom.Model.RoomId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatsController {
    private ChatRoomAPI.getChatsCallBack getChatsCallBack;

    public ChatsController(ChatRoomAPI.getChatsCallBack getChatsCallBack) {
        this.getChatsCallBack = getChatsCallBack;
    }

    public void start(final String accessToken, RoomId roomId){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<ChatResponse> call = chatRoomAPI.getChats(accessToken,roomId);
        call.enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful())
                    getChatsCallBack.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                getChatsCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
