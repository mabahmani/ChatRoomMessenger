package com.mab.chatroom.Data;

import android.util.Log;
import android.widget.Toast;

import com.mab.chatroom.Model.RoomResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsController {
    private ChatRoomAPI.getRoomsCallBack getRoomsCallBack;

    public RoomsController(ChatRoomAPI.getRoomsCallBack getRoomsCallBack) {
        this.getRoomsCallBack = getRoomsCallBack;
    }

    public void start(final String accessToken){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<RoomResponse> call = chatRoomAPI.getRooms(accessToken);
        call.enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                if(response.isSuccessful()){
                    getRoomsCallBack.onResponse(response.body().getRooms());
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                getRoomsCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
