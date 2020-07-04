package com.example.modules;

public class History {
    private String startTime;
    private String endTime;
    private int id;
    public History(int id,String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.id=id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
