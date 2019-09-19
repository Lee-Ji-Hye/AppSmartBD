package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCarVO implements Serializable {

    @SerializedName("userid")
    private String userid; //회원 아이디
    @SerializedName("name")
    private String name; //이름
    @SerializedName("hp")
    private String hp; //전화번호
    @SerializedName("c_num")
    private String c_num; //차번호
    @SerializedName("kind_of_car")
    private String kind_of_car; //차종

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getC_num() {
        return c_num;
    }

    public void setC_num(String c_num) {
        this.c_num = c_num;
    }

    public String getKind_of_car() {
        return kind_of_car;
    }

    public void setKind_of_car(String kind_of_car) {
        this.kind_of_car = kind_of_car;
    }
}
