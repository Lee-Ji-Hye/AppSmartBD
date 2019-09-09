package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodCouponVO extends BaseRespon {
    @SerializedName("couponList")
    private ArrayList<Coupon> couponList;

    public FoodCouponVO()
    {

    }
    public ArrayList<Coupon> getCouponList() {
        return couponList;
    }

    public class Coupon {
        //쿠폰 tbl
        @SerializedName("useYN")
        private String useYN;
        @SerializedName("f_coupon_num")
        private String f_coupon_num;
        @SerializedName("f_coupon_name")
        private String f_coupon_name;
        @SerializedName("comp_org")
        private String comp_org;
        @SerializedName("comp_seq")
        private String comp_seq;
        @SerializedName("fcoupon_price")
        private String fcoupon_price;
        @SerializedName("staff_id")
        private String staff_id;
        @SerializedName("f_coupon_regidate")
        private String f_coupon_regidate;
        @SerializedName("f_coupon_start")
        private String f_coupon_start;
        @SerializedName("f_coupon_end")
        private String f_coupon_end;
        @SerializedName("f_serial")
        private String f_serial;
        @SerializedName("f_major")
        private String f_major;
        @SerializedName("f_minor")
        private String f_minor;

        public String getF_major() {
            return f_major;
        }

        public String getF_minor() {
            return f_minor;
        }

        public void setF_major(String f_major) {
            this.f_major = f_major;
        }

        public void setF_minor(String f_minor) {
            this.f_minor = f_minor;
        }

        public String getF_coupon_name() {
            return f_coupon_name;
        }

        public void setF_coupon_name(String f_coupon_name) {
            this.f_coupon_name = f_coupon_name;
        }

        public void setComp_org(String comp_org) {
            this.comp_org = comp_org;
        }

        public String getComp_org() {
            return comp_org;
        }

        public String getF_serial() {
            return f_serial;
        }

        public void setF_serial(String f_serial) {
            this.f_serial = f_serial;
        }

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

        public String getComp_seq() {
            return comp_seq;
        }

        public void setComp_seq(String comp_seq) {
            this.comp_seq = comp_seq;
        }

        public String getFcoupon_price() {
            return fcoupon_price;
        }

        public void setFcoupon_price(String fcoupon_price) {
            this.fcoupon_price = fcoupon_price;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
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

        /*comp_seq       NUMBER(7) NOT NULL,    --업체코드
        f_coupon_name  VARCHAR2(30)   NOT NULL,    --이름
        f_coupon_price NUMBER(7)    NOT NULL,    --가격
        staff_id       VARCHAR2(50),             --등록자 아이디
        f_coupon_regidate TIMESTAMP DEFAULT SYSDATE, --쿠폰 등록일
        f_coupon_start VARCHAR2(20)    NOT NULL,    --유효기간1
        f_coupon_end   VARCHAR2(20)    NOT NULL     --유효기간2*/

        //시리얼정보 tbl
/*
f_serial       VARCHAR2(50) PRIMARY KEY, --시리얼코드
    f_coupon_num   VARCHAR2(20) NOT NULL,    --쿠폰번호
    userid         VARCHAR2(50) NULL,        --아이디
    f_coupon_use   CHAR(1) DEFAULT 'N',      --사용여부
    f_use_date     TIMESTAMP                 --쿠폰 사용일
        */



    }
}
