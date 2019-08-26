package com.team.smart.vo;

import java.util.Date;

//주차권 상품 VO -양은지
public class ParkingTicket {

    private String p_code; //주차상품코드
    private String b_code; //건물코드
    private String b_name; //건물이름
    private String address;//건물주소
    private int hourly; // 시간
    private int price; //가격
    private int count; //장수
    private String reg_id; //등록자
    private Date reg_date; //등록일

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //보유한 주차권
    public ParkingTicket(String b_code, String b_name, String address, int hourly, int count) {
        this.b_code = b_code;
        this.b_name = b_name;
        this.address = address;
        this.hourly = hourly;
        this.count = count;
    }

    public ParkingTicket(String p_code, String b_code, int hourly, int price) {
        this.p_code = p_code;
        this.b_code = b_code;
        this.hourly = hourly;
        this.price = price;
    }

    public ParkingTicket(String p_code, String b_code, int hourly, int price, String reg_id, Date reg_date) {
        this.p_code = p_code;
        this.b_code = b_code;
        this.hourly = hourly;
        this.price = price;
        this.reg_id = reg_id;
        this.reg_date = reg_date;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getB_code() {
        return b_code;
    }

    public void setB_code(String b_code) {
        this.b_code = b_code;
    }

    public int getHourly() {
        return hourly;
    }

    public void setHourly(int hourly) {
        this.hourly = hourly;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }
}
