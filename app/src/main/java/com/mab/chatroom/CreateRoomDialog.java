package com.mab.chatroom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.EditText;

public class CreateRoomDialog extends Dialog {
    private EditText roomName;
    private Button create;

    public CreateRoomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_creat_room);
        findViews();

        getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }

    private void findViews(){
        roomName = findViewById(R.id.room_name);
        create = findViewById(R.id.create);
    }
}
