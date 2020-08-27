package com.example.readyouting;

import android.widget.ImageView;

public class Hourly_assign {


    String temperature, time, description_tot;
    int imageView;

    public Hourly_assign(String temperature, String time, int imageView, String description_tot){
        this.time = time;
        this.temperature = temperature;
        this.imageView = imageView;
        this.description_tot =description_tot;
    }


    public String getDescription_tot() {
        return description_tot;
    }

    public void setDescription_tot(String description_tot) {
        this.description_tot = description_tot;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
