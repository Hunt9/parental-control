package com.example.dell.androidhive;

public class ListCall_Class {

    private String dir;
    private String phNumber;
    private String callDuration;
    private String callDayTime;

    public ListCall_Class() {
    }

    public ListCall_Class(String dir, String phNumber, String callDuration, String callDayTime) {
        this.dir = dir;
        this.phNumber = phNumber;
        this.callDuration = callDuration;
        this.callDayTime = callDayTime;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDayTime() {
        return callDayTime;
    }

    public void setCallDayTime(String callDayTime) {
        this.callDayTime = callDayTime;
    }
}
