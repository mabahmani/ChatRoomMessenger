package com.mab.chatroom.Data;

import com.mab.chatroom.Model.Chat;
import com.mab.chatroom.Model.ChatResponse;
import com.mab.chatroom.Model.Room;
import com.mab.chatroom.Model.RoomId;
import com.mab.chatroom.Model.RoomResponse;
import com.mab.chatroom.Model.SendMessage;
import com.mab.chatroom.Model.SendMessageResponse;
import com.mab.chatroom.Model.TokenResponse;
import com.mab.chatroom.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatRoomAPI {
    String BASE_URL = "https://api.backtory.com/";

    @Headers("X-Backtory-Authentication-Id:5a1d4b3de4b0afa16474fabd")
    @POST("auth/users")
    Call<User> registerUser(@Body User user);


    @Headers({
            "X-Backtory-Authentication-Id:5a1d4b3de4b0afa16474fabd",
            "X-Backtory-Authentication-Key:5a1d4b3de4b0ce09cd4655c8"
    })
    @FormUrlEncoded
    @POST("auth/login")
    Call<TokenResponse> userLogin(@Field("username") String username,
                                  @Field("password") String password);

    @Headers("X-Backtory-Object-Storage-Id:5a1d4b3de4b03ffa047badf5")
    @POST("object-storage/classes/query/Room")
    Call<RoomResponse> getRooms(
            @Header("Authorization") String accessToken
    );

    @Headers("X-Backtory-Object-Storage-Id:5a1d4b3de4b03ffa047badf5")
    @POST("object-storage/classes/query/Message")
    Call<ChatResponse> getChats(
            @Header("Authorization") String accessToken,
            @Body RoomId roomId
    );

    @Headers("X-Backtory-Object-Storage-Id:5a1d4b3de4b03ffa047badf5")
    @POST("object-storage/classes/Message")
    Call<SendMessageResponse> sendMessage(
            @Header("Authorization") String accessToken,
            @Body SendMessage sendMessage
    );

    interface RegisterUserCallBack{
        void onResponse(boolean successful, String errorMessage, User user);
        void onFailure(String cause);
    }

    interface LoginUserCallBack{
        void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse );
        void onFailure(String cause);
    }

    interface getRoomsCallBack{
        void onResponse(List<Room> rooms);
        void onFailure(String cause);
    }

    interface getChatsCallBack{
        void onResponse(ChatResponse chatResponse);
        void onFailure(String cause);
    }

    interface sendMessageCallBack{
        void onResponse(SendMessageResponse sendMessageResponse);
        void onFailure(String cause);
    }
}
