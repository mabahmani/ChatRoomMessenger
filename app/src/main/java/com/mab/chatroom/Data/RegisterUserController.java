package com.mab.chatroom.Data;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.mab.chatroom.Model.ErrorResponse;
import com.mab.chatroom.Model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterUserController {
    private ChatRoomAPI.RegisterUserCallBack registerUserCallBack;

    public RegisterUserController(ChatRoomAPI.RegisterUserCallBack registerUserCallBack) {
        this.registerUserCallBack = registerUserCallBack;
    }

    public void start(User user){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ChatRoomAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatRoomAPI chatRoomAPI = retrofit.create(ChatRoomAPI.class);
        Call<User> call = chatRoomAPI.registerUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()){
                    registerUserCallBack.onResponse(true, null, response.body());
                }

                else {
                    try {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(), ErrorResponse.class);
                        registerUserCallBack.onResponse(false,errorResponse.getMessage(), null);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
            }
        });
    }
}
