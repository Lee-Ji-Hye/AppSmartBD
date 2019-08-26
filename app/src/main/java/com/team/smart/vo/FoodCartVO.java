package com.team.smart.vo;

import java.io.Serializable;

//FoodCartVO는 Activity나 밖에서 ArrayList<FoodCartVO>로 쌓일것임.
public class FoodCartVO implements Serializable {

    private String comp_seq;    //업체코드
    private String comp_org;    //업체명
    private String f_code;      //메뉴코드
    private String f_catagory;  //카테고리
    private String f_name;      //메뉴명
    private String f_price;     //메뉴가격
    private int f_cnt;          //수량

    public void setComp_seq(String comp_seq) {
        this.comp_seq = comp_seq;
    }

    public String getComp_seq() {
        return comp_seq;
    }

    public String getComp_org() { return comp_org; }

    public void setComp_org(String comp_org) { this.comp_org = comp_org; }

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_catagory() {
        return f_catagory;
    }

    public void setF_catagory(String f_catagory) {
        this.f_catagory = f_catagory;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_price() {
        return f_price;
    }

    public void setF_price(String f_price) {
        this.f_price = f_price;
    }

    public int getF_cnt() {
        return f_cnt;
    }

    public void setF_cnt(int f_cnt) {
        this.f_cnt = f_cnt;
    }
}