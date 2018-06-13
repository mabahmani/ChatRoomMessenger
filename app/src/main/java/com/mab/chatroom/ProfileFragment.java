package com.mab.chatroom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mab.chatroom.Data.UserPreferenceManager;

public class ProfileFragment extends Fragment {
    private TextView username;
    private Button logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);

        username.setText(UserPreferenceManager.getInstance(getActivity()).getUsername());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPreferenceManager.getInstance(getActivity()).clear();
                getActivity().finish();
            }
        });
    }

    private void findViews(View view){
       username = view.findViewById(R.id.username);
       logout = view.findViewById(R.id.logout);
    }
}
