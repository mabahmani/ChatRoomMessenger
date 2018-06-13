package com.mab.chatroom.Model;

public class SendMessage {
    private String roomId;
    private String text;
    private String username;

    public SendMessage(String roomId, String text, String username) {
        this.roomId = roomId;
        this.text = text;
        this.username = username;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
