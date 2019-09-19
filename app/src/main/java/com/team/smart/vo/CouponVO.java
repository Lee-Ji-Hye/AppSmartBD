package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CouponVO  implements Serializable {

    //쿠폰 tbl
    private String useYN;
    private String f_coupon_num;
    private String f_coupon_name;
    private String fcoupon_price;
    private String f_coupon_regidate;
    private String f_coupon_start;
    private String f_coupon_end;
    private String f_serial;

    public String getUseYN() {
        return useYN;
    }

    public void setUseYN(String useYN) {
        this.useYN = useYN;
    }

    public String getF_coupon_num() {
        return f_coupon_num;
    }

    public void setF_coupon_num(String f_coupon_num) {
        this.f_coupon_num = f_coupon_num;
    }

    public String getF_coupon_name() {
        return f_coupon_name;
    }

    public void setF_coupon_name(String f_coupon_name) {
        this.f_coupon_name = f_coupon_name;
    }

    public String getFcoupon_price() {
        return fcoupon_price;
    }

    public void setFcoupon_price(String fcoupon_price) {
        this.fcoupon_price = fcoupon_price;
    }

    public String getF_coupon_regidate() {
        return f_coupon_regidate;
    }

    public void setF_coupon_regidate(String f_coupon_regidate) {
        this.f_coupon_regidate = f_coupon_regidate;
    }

    public String getF_coupon_start() {
        return f_coupon_start;
    }

    public void setF_coupon_start(String f_coupon_start) {
        this.f_coupon_start = f_coupon_start;
    }

    public String getF_coupon_end() {
        return f_coupon_end;
    }

    public void setF_coupon_end(String f_coupon_end) {
        this.f_coupon_end = f_coupon_end;
    }

    public String getF_serial() {
        return f_serial;
    }

    public void setF_serial(String f_serial) {
        this.f_serial = f_serial;
    }
}
