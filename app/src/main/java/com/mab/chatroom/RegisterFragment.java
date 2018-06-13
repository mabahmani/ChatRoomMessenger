package com.mab.chatroom;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mab.chatroom.Data.ChatRoomAPI;
import com.mab.chatroom.Data.LoginUserController;
import com.mab.chatroom.Data.RegisterUserController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.TokenResponse;
import com.mab.chatroom.Model.User;

public class RegisterFragment extends android.support.v4.app.Fragment {
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText username;
    private EditText password;
    private TextView register;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        setOnClicks();
    }

    private void setOnClicks(){

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.username_requierd,Toast.LENGTH_LONG).show();
                }
                else if(password.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.password_requierd,Toast.LENGTH_LONG).show();
                }
                else if(email.getText().toString().equals("")){
                    Toast.makeText(getActivity(), R.string.email_requierd,Toast.LENGTH_LONG).show();
                }

                else{
                    User user = new User(
                            username.getText().toString(),
                            password.getText().toString(),
                            email.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString());
                    final String tempPassword = user.getPassword();
                    final String tempUsername = user.getUsername();
                    ChatRoomAPI.RegisterUserCallBack registerUserCallBack = new ChatRoomAPI.RegisterUserCallBack() {
                        @Override
                        public void onResponse(boolean successful, String errorMessage, User user) {
                            if(successful){
                                login(tempUsername,tempPassword);
                            }

                            else {
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(String cause) {

                        }
                    };

                    RegisterUserController registerUserController = new RegisterUserController(registerUserCallBack);
                    registerUserController.start(user);
                }
            }
        });
    }

    private void login(final String username , String password){
        ChatRoomAPI.LoginUserCallBack loginUserCallBack = new ChatRoomAPI.LoginUserCallBack() {
            @Override
            public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
                if(successful){
                    UserPreferenceManager.getInstance(getActivity()).putAccessToken(tokenResponse.getAccessToken());
                    UserPreferenceManager.getInstance(getActivity()).putUsername(username);
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
        loginUserController.start(username,password);
    }



    private void findViews(View view){
        firstName = view.findViewById(R.id.firstName);
        lastName = view.findViewById(R.id.lastName);
        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        register = view.findViewById(R.id.register);
    }
}
