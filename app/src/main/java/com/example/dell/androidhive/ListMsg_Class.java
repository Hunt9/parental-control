package com.example.dell.androidhive;

public class ListMsg_Class {

    private String address;
    private String body;

    public ListMsg_Class() {
    }

    public ListMsg_Class(String address, String body) {
        this.address = address;
        this.body = body;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
