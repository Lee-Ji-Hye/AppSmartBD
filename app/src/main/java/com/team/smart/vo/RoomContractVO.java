package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

public class RoomContractVO {

    @SerializedName("rt_code")
    private String rt_code;       //계약 코드
    @SerializedName("rt_hash")
    private String rt_hash;       //계약 해쉬코드
    @SerializedName("r_code")
    private String r_code;        //매물 코드
    @SerializedName("rt_state")
    private String rt_state;      //계약 상태
    @SerializedName("userid")
    private String userid;        //사용자 아이디
    @SerializedName("rt_mobile")
    private String rt_mobile;     //임차인 휴대폰
    @SerializedName("rt_email")
    private String rt_email;      //임차인 이메일
    @SerializedName("rt_date1")
    private String rt_date1;      //계약 날짜
    @SerializedName("rt_date2")
    private String rt_date2;      //계약 만기일
    @SerializedName("rt_deposit")
    private String rt_deposit;    //계약 보증금
    @SerializedName("rt_price")
    private String rt_price;      //계약 월세
    @SerializedName("staff_id")
    private String staff_id;      //관리자 아이디

    public String getRt_code() { return rt_code; }

    public void setRt_code(String rt_code) { this.rt_code = rt_code; }

    public String getRt_hash() { return rt_hash; }

    public void setRt_hash(String rt_hash) { this.rt_hash = rt_hash; }

    public String getR_code() { return r_code; }

    public void setR_code(String r_code) { this.r_code = r_code; }

    public String getRt_state() { return rt_state; }

    public void setRt_state(String rt_state) { this.rt_state = rt_state; }

    public String getUserid() { return userid; }

    public void setUserid(String userid) { this.userid = userid; }

    public String getRt_mobile() { return rt_mobile; }

    public void setRt_mobile(String rt_mobile) { this.rt_mobile = rt_mobile; }

    public String getRt_email() { return rt_email; }

    public void setRt_email(String rt_email) { this.rt_email = rt_email; }

    public String getRt_date1() { return rt_date1; }

    public void setRt_date1(String rt_date1) { this.rt_date1 = rt_date1; }

    public String getRt_date2() { return rt_date2; }

    public void setRt_date2(String rt_date2) { this.rt_date2 = rt_date2; }

    public String getRt_deposit() { return rt_deposit; }

    public void setRt_deposit(String rt_deposit) { this.rt_deposit = rt_deposit; }

    public String getRt_price() { return rt_price; }

    public void setRt_price(String rt_price) { this.rt_price = rt_price; }

    public String getStaff_id() { return staff_id; }

    public void setStaff_id(String staff_id) { this.staff_id = staff_id; }
}
