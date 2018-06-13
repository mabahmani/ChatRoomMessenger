package com.mab.chatroom.Data;

import com.mab.chatroom.Model.CreateRoomResponse;
import com.mab.chatroom.Model.RoomName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateRoomController {
    private ChatRoomAPI.createRoomCallBack createRoomCallBack;

    public CreateRoomController(ChatRoomAPI.createRoomCallBack createRoomCallBack) {
        this.createRoomCallBack = createRoomCallBack;
    }

    public void start(String accessToken, RoomName roomName){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<CreateRoomResponse> call = chatRoomAPI.createRoom(accessToken,roomName);
        call.enqueue(new Callback<CreateRoomResponse>() {
            @Override
            public void onResponse(Call<CreateRoomResponse> call, Response<CreateRoomResponse> response) {
                if(response.isSuccessful()){
                    createRoomCallBack.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<CreateRoomResponse> call, Throwable t) {
                createRoomCallBack.onFailure(t.getCause().toString());
            }
        });
    }

}
