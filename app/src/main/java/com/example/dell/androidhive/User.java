package com.example.dell.androidhive;

import java.util.Map;

public class User {

    public String name, email, phone, age,pass;
    public String id;
    public String childId;
    public String parentID;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
