package com.cranberryanalytics.techlabassignment.main.model;

public class MainItem {
    String text;
    String image;
    float rate;
    boolean isEnabled;
    int type;

    public MainItem() {
        text = "";
        image = "";
        rate = 0;
        isEnabled = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
