package com.mab.chatroom.Model;

public class ErrorResponse {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage(){
        switch (message){

            case "email conflict":
                return "این ایمیل قبلا به ثبت رسیده است.";
            case "username conflict":
                return "این نام کاربری قبلا انتخاب شده است.";
            case "Email is invalid.":
                return "ایمیل وارد شده معتبر نیست.";

            default:
                return null;
        }
    }
}
