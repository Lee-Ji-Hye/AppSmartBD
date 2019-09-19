package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//주차권 요금 VO -양은지
public class ParkingBasicVO extends BaseRespon {

    @SerializedName("basicPrices")
    private ArrayList<ParkingBasicVO.basicInfo> basicPrices; //메뉴정보
    public ParkingBasicVO() {
        basicPrices = new ArrayList<>();
    }
    public ArrayList<ParkingBasicVO.basicInfo> getbasicPrices() {
        return basicPrices;
    }

    public class basicInfo {
        @SerializedName("bp_seq")
        private int bp_seq; // 시퀀스
        @SerializedName("b_code")
        private String b_code; // 건물코드
        @SerializedName("userid")
        private String userid; //구매자
        @SerializedName("bp_type")
        private String bp_type; // 주차시간타입 h:시간당   ㅡm:분당
        @SerializedName("pb_time")
        private int pb_time; // 주차시간
        @SerializedName("pb_price")
        private int pb_price; // 주차요금
        @SerializedName("pb_free")
        private int pb_free; // 초과되면 금액이 부과되는 시간 ex) 10분 초과시 10   분만 입력
        @SerializedName("pb_free_price")
        private int pb_free_price; // 초과되면 금액이 부과되는 금액
        @SerializedName("reg_id")
        private String reg_id; //  등록자
        @SerializedName("reg_date")
        private String reg_date; // 등록일

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getBp_seq() {
            return bp_seq;
        }

        public void setBp_seq(int bp_seq) {
            this.bp_seq = bp_seq;
        }

        public String getB_code() {
            return b_code;
        }

        public void setB_code(String b_code) {
            this.b_code = b_code;
        }

        public String getBp_type() {
            return bp_type;
        }

        public void setBp_type(String bp_type) {
            this.bp_type = bp_type;
        }

        public int getPb_time() {
            return pb_time;
        }

        public void setPb_time(int pb_time) {
            this.pb_time = pb_time;
        }

        public int getPb_price() {
            return pb_price;
        }

        public void setPb_price(int pb_price) {
            this.pb_price = pb_price;
        }

        public int getPb_free() {
            return pb_free;
        }

        public void setPb_free(int pb_free) {
            this.pb_free = pb_free;
        }

        public int getPb_free_price() {
            return pb_free_price;
        }

        public void setPb_free_price(int pb_free_price) {
            this.pb_free_price = pb_free_price;
        }

        public String getReg_id() {
            return reg_id;
        }

        public void setReg_id(String reg_id) {
            this.reg_id = reg_id;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }
    }
}
