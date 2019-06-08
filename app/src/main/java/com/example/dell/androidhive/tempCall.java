package com.example.dell.androidhive;

public class tempCall {

    String phNumber;  //Phone Number
    String dir;  //Call Type
    String callDayTime;  //Call Date
    String callDuration;  //Call duration in sec

    public tempCall(String phNumber, String dir, String callDayTime, String callDuration) {
        this.phNumber = phNumber;
        this.dir = dir;
        this.callDayTime = callDayTime;
        this.callDuration = callDuration;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getCallDayTime() {
        return callDayTime;
    }

    public void setCallDayTime(String callDayTime) {
        this.callDayTime = callDayTime;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }
}
