package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;

//주차권 결제정보 VO -양은지
public class ParkingPayOrderDetailVO extends BaseRespon {

    @SerializedName("orderDetail")
    private ArrayList<ParkingPayOrderDetailVO.payOrder> payOrders; //메뉴정보
    public ParkingPayOrderDetailVO() {
        payOrders = new ArrayList<>();
    }
    public ArrayList<ParkingPayOrderDetailVO.payOrder> getPayOrders() {
        return payOrders;
    }

    public class payOrder {
        @SerializedName("pay_seq")
        private String pay_seq; // 결제 코드
        @SerializedName("inoutcode")
        private String inoutcode; // 입출차코드
        @SerializedName("userid")
        private String userid; // 아이디
        @SerializedName("pay_price")
        private int pay_price; // 결제 금액
        @SerializedName("pay_type")
        private String pay_type; //  결제구분 :: TICKET, MONEY
        @SerializedName("pay_enable_time")
        private String pay_enable_time; // 주차~결제시간
        @SerializedName("parking_code")
        private String parking_code; //  주차권코드
        @SerializedName("pay_day")
        private String pay_day; //  결제시간
        @SerializedName("tid")
        private String tid; // 카카오페이
        @SerializedName("carnum")
        private String carnum; //차번호
        @SerializedName("pb_state")
        private String pb_state; //상태 상태 0:결제대기, 1:결제요청완료 2:결제 완료

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

        public String getPay_enable_time() {
            return pay_enable_time;
        }

        public void setPay_enable_time(String pay_enable_time) {
            this.pay_enable_time = pay_enable_time;
        }

        public String getParking_code() {
            return parking_code;
        }

        public void setParking_code(String parking_code) {
            this.parking_code = parking_code;
        }

        public String getPay_day() {
            return pay_day;
        }

        public void setPay_day(String pay_day) {
            this.pay_day = pay_day;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public String getCarnum() {
            return carnum;
        }

        public void setCarnum(String carnum) {
            this.carnum = carnum;
        }

        public String getPb_state() {
            return pb_state;
        }

        public void setPb_state(String pb_state) {
            this.pb_state = pb_state;
        }
    }
}
