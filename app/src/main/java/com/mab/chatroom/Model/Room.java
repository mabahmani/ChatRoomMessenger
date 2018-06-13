package com.mab.chatroom.Model;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("_id")
    private String id;

    private String name;

    Room(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
