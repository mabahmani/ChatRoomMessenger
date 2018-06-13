package com.mab.chatroom.Data;

import android.support.annotation.NonNull;

import com.mab.chatroom.Model.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserController {
    private ChatRoomAPI.LoginUserCallBack loginUserCallBack;

    public LoginUserController(ChatRoomAPI.LoginUserCallBack loginUserCallBack) {
        this.loginUserCallBack = loginUserCallBack;
    }

    public void start(String username, String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<TokenResponse> call = chatRoomAPI.userLogin(username,password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponse> call, @NonNull Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    loginUserCallBack.onResponse(true,null,response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenResponse> call, @NonNull Throwable t) {
                    loginUserCallBack.onFailure(t.getCause().toString());
            }
        });
    }
}
