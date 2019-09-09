package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestUserVO implements Serializable {
    @SerializedName("userid")
    private String userid;
    @SerializedName("userpw")
    private String userpw;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("hp")
    private String hp;
    @SerializedName("regidate")
    private String regidate;
    @SerializedName("visit")
    private String visit;
    @SerializedName("visit_date")
    private String visit_date;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getRegidate() {
        return regidate;
    }

    public void setRegidate(String regidate) {
        this.regidate = regidate;
    }

    public String getVisit() {
        return visit;
    }

    public void setVisit(String visit) {
        this.visit = visit;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }
}
