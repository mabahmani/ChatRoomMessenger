package com.mab.chatroom;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mab.chatroom.Data.ChatRoomAPI;
import com.mab.chatroom.Data.LoginUserController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.TokenResponse;

public class LoginFragment extends android.support.v4.app.Fragment {
    private EditText username;
    private EditText password;
    private TextView login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log_in,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews(view);
        setOnClicks();
    }

    private void setOnClicks(){

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.username_password_rquiered,Toast.LENGTH_LONG).show();
                }
                else {
                    ChatRoomAPI.LoginUserCallBack loginUserCallBack = new ChatRoomAPI.LoginUserCallBack() {
                        @Override
                        public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
                            if(successful){
                                UserPreferenceManager.getInstance(getActivity()).putAccessToken(tokenResponse.getAccessToken());
                                UserPreferenceManager.getInstance(getActivity()).putUsername(username.getText().toString());
                                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(
                                        new Intent("login_successful")
                                );
                            }
                        }

                        @Override
                        public void onFailure(String cause) {
                            Toast.makeText(getActivity(),cause,Toast.LENGTH_LONG).show();
                        }
                    };

                    LoginUserController loginUserController = new LoginUserController(loginUserCallBack);
                    loginUserController.start(username.getText().toString(),password.getText().toString());
                }
            }
        });
    }

    private void findViews(View view){
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
    }
}
