package com.mab.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mab.chatroom.Data.ChatRoomAPI;
import com.mab.chatroom.Data.RoomsController;
import com.mab.chatroom.Data.UserPreferenceManager;
import com.mab.chatroom.Model.Room;
import com.mab.chatroom.Model.RoomAdapter;
import com.mab.chatroom.Model.RoomId;

import java.util.ArrayList;
import java.util.List;


public class RoomsFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> rooms = new ArrayList<>();

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rooms,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initRoomsList();
        progressBar.setVisibility(View.VISIBLE);
        ChatRoomAPI.getRoomsCallBack getRoomsCallBack = new ChatRoomAPI.getRoomsCallBack() {
            @Override
            public void onResponse(List<Room> inputRooms) {
                rooms.clear();
                rooms.addAll(inputRooms);
                roomAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(String cause) {
                Toast.makeText(getActivity(),cause,Toast.LENGTH_LONG).show();
            }
        };

        RoomsController roomsController = new RoomsController(getRoomsCallBack);
        roomsController.start(
                "bearer " + UserPreferenceManager.getInstance(getActivity()).getAccessToken()
        );
    }

    private void initRoomsList(){
        roomAdapter = new RoomAdapter(rooms, new RoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Room item) {
                RoomId roomId = new RoomId();
                roomId.setId(item.getId());

                Gson gson = new Gson();
                String roomIdToJson = gson.toJson(roomId);

                Intent intent = new Intent(getActivity().getBaseContext(),ChatsActivity.class);
                intent.putExtra("roomId",roomIdToJson);
                getActivity().startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(roomAdapter);
    }

    private void findViews(View view){
        recyclerView = view.findViewById(R.id.rooms_list);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    @Override
    public void onResume() {
        super.onResume();
        roomAdapter.notifyDataSetChanged();
    }
}
