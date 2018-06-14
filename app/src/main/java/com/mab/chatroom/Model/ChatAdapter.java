package com.mab.chatroom.Model;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mab.chatroom.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context context;
    private List<Chat> chats;
    private String username;

    public ChatAdapter(Context context , List<Chat> chats, String username){
        this.context = context;
        this.chats = chats;
        this.username = username;
    }

    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chats_item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (chats.get(position).getUsername() == null){
            holder.username.setText(R.string.unknown);
            holder.text.setText(chats.get(position).getText());
        }
        else {
            if (chats.get(position).getUsername().equals(username)) {
                holder.messageLayout.setGravity(Gravity.END | Gravity.RIGHT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.messageHolder.setBackground(context.getResources().getDrawable(R.drawable.dark_gray));
                }
                holder.username.setText(chats.get(position).getUsername());
                holder.text.setText(chats.get(position).getText());
            }
            else {
                holder.messageLayout.setGravity(Gravity.START | Gravity.LEFT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.messageHolder.setBackground(context.getResources().getDrawable(R.drawable.gray));
                }
                holder.username.setText(chats.get(position).getUsername());
                holder.text.setText(chats.get(position).getText());
            }
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView text;
        private LinearLayout messageLayout;
        private LinearLayout messageHolder;
        public ViewHolder(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            text = itemView.findViewById(R.id.chat_text);
            messageLayout = itemView.findViewById(R.id.message_layout);
            messageHolder = itemView.findViewById(R.id.message_holder);
        }
    }
}
