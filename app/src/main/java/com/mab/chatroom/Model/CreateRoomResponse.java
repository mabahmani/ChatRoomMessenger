package com.mab.chatroom.Model;

import com.google.gson.annotations.SerializedName;

public class CreateRoomResponse {
    private String createdAt;

    @SerializedName("_id")
    private String id;

    public CreateRoomResponse(){

    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
