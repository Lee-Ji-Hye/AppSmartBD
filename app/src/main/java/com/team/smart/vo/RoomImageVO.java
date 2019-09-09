package com.team.smart.vo;

public class RoomImageVO {

    String r_code;
    Integer r_img;

    public RoomImageVO(String r_code, Integer r_img) {
        this.r_code = r_code;
        this.r_img = r_img;
    }

    public String getR_code() {
        return r_code;
    }

    public void setR_code(String r_code) {
        this.r_code = r_code;
    }

    public Integer getR_img() {
        return r_img;
    }

    public void setR_img(Integer r_img) {
        this.r_img = r_img;
    }

}
