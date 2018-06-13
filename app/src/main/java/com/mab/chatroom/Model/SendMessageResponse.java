package com.mab.chatroom.Model;

import com.google.gson.annotations.SerializedName;

public class SendMessageResponse {
    @SerializedName("_id")
    private String id;
    private String createdAt;

    public SendMessageResponse(){

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
