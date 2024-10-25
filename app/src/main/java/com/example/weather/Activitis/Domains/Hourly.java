package com.example.weather.Activitis.Domains;

public class Hourly {
    private String Hour;
    private int temp;
    private String picPath;

    public Hourly(String hour, int temp, String picPath) {
        Hour = hour;
        this.temp = temp;
        this.picPath = picPath;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
