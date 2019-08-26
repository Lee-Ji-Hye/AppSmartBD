package com.team.smart.vo;

public class ParkingMarkerItem {

    double lat; // 위도
    double lon; // 경도
    String b_name;  // 이름
    String b_code; //건물코드

    public ParkingMarkerItem(double lat, double lon, String b_name,String b_code) {
        this.lat = lat;
        this.lon = lon;
        this.b_name = b_name;
        this.b_code= b_code;
    }

    public String getB_code() {
        return b_code;
    }

    public void setB_code(String b_code) {
        this.b_code = b_code;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
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


}