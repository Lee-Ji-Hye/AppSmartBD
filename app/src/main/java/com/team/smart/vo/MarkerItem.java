package com.team.smart.vo;

public class MarkerItem {

    double lat; // 위도
    double lon; // 경도
    int price;  // 가격

    public MarkerItem(double lat, double lon, int price) {
        this.lat = lat;
        this.lon = lon;
        this.price = price;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}