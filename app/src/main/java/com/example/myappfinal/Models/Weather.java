package com.example.myappfinal.Models;

public class Weather {
    public String degrees;
    public String time;

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getDegrees(){ return degrees; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return
                "Weather{" +
                        "degrees = '" + degrees + '\'' +
                        ",time = '" + time + '\'' +
                        "}";
    }
}
