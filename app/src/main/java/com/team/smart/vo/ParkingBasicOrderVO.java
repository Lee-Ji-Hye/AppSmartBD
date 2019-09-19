package com.team.smart.vo;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;

//주차장 요금 결제시 정보 보낼때 vo
public class ParkingBasicOrderVO implements Serializable {

    private String pay_seq; // 결제 코드
    private String inoutcode; // 입출차코드
    private String userid; // 아이디
    private int pay_price; // 결제 금액
    private String pay_type; //  결제구분 :: TICKET, MONEY
    private int pay_enable_time; // 주차~결제시간
    private String parking_code; //  주차권코드
    private Timestamp pay_day; //  결제시간
    private String tid; // 카카오페이
    private String carnum; //차번호
    private int pb_state; //상태

    public int getPb_state() {
        return pb_state;
    }

    public void setPb_state(int pb_state) {
        this.pb_state = pb_state;
    }

    public String getCarnum() {
        return carnum;
    }

    public void setCarnum(String carnum) {
        this.carnum = carnum;
    }

    public String getPay_seq() {
        return pay_seq;
    }

    public void setPay_seq(String pay_seq) {
        this.pay_seq = pay_seq;
    }

    public String getInoutcode() {
        return inoutcode;
    }

    public void setInoutcode(String inoutcode) {
        this.inoutcode = inoutcode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getPay_price() {
        return pay_price;
    }

    public void setPay_price(int pay_price) {
        this.pay_price = pay_price;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public int getPay_enable_time() {
        return pay_enable_time;
    }

    public void setPay_enable_time(int pay_enable_time) {
        this.pay_enable_time = pay_enable_time;
    }

    public String getParking_code() {
        return parking_code;
    }

    public void setParking_code(String parking_code) {
        this.parking_code = parking_code;
    }

    public Timestamp getPay_day() {
        return pay_day;
    }

    public void setPay_day(Timestamp pay_day) {
        this.pay_day = pay_day;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
