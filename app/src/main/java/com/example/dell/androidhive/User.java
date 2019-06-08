package com.example.dell.androidhive;

import java.util.Map;

public class User {

    public String name, email, phone, age;
    public String id;
    public String childId;

    public User(String email, String name, String phone, String age, String childId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.childId = childId;
    }

    public User(){

    }

    public User(String _email, String _name, String _phone, String _age) {
        this.email = _email;
        this.name = _name;
        this.phone = _phone;
        this.age = _age;
    }

    public User(String _email) {
        this.email = _email;
    }

//    public User(String id){
//        this.id = id;
//    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }


}
