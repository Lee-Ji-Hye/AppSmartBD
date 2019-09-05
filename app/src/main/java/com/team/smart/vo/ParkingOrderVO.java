package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class ParkingOrderVO implements Serializable {

    public ParkingOrderVO() {
    }

    //주차권상품코드,아이디,가격,수량
    public ParkingOrderVO(String p_code, String userid, String p_oprice, String p_count) {
        this.p_code = p_code;
        this.userid = userid;
        this.p_oprice = p_oprice;
        this.p_count = p_count;
    }

    private String p_ocode;// 결제 코드
    private String p_code;// 주차상품코드
    private String userid; // 아이디
    private String p_state;// 상태
    private String p_oprice;// 가격
    private Timestamp pay_day; //구매일
    private String p_count; //구매 수량
    private Timestamp refund_day;// 결제일
    private String tid;

    public Timestamp getRefund_day() {
        return refund_day;
    }

    public void setRefund_day(Timestamp refund_day) {
        this.refund_day = refund_day;
    }

    public String getP_ocode() {
        return p_ocode;
    }

    public void setP_ocode(String p_ocode) {
        this.p_ocode = p_ocode;
    }

    public String getP_oprice() {
        return p_oprice;
    }

    public void setP_oprice(String p_oprice) {
        this.p_oprice = p_oprice;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getP_state() {
        return p_state;
    }

    public void setP_state(String p_state) {
        this.p_state = p_state;
    }

    public Timestamp getPay_day() {
        return pay_day;
    }

    public void setPay_day(Timestamp pay_day) {
        this.pay_day = pay_day;
    }

    public String getP_count() {
        return p_count;
    }

    public void setP_count(String p_count) {
        this.p_count = p_count;
    }
}
