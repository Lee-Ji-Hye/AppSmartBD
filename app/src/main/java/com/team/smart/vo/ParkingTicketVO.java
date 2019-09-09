package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;

//주차권 상품 VO -양은지
public class ParkingTicketVO extends BaseRespon {

    @SerializedName("pTickets")
    private ArrayList<ParkingTicketVO.ParkingTicket> parkingTickets; //메뉴정보
    public ParkingTicketVO() {
        parkingTickets = new ArrayList<>();
    }
    public ArrayList<ParkingTicketVO.ParkingTicket> getParkingTickets() {
        return parkingTickets;
    }

    public class ParkingTicket {
        @SerializedName("p_code")
        private String p_code; //주차상품코드
        @SerializedName("b_code")
        private String b_code; //건물코드

        @SerializedName("p_type")
        private String p_type; // 주차시간타입 h:시간당   ㅡm:분당
        @SerializedName("hourly")
        private String hourly; // 0이하는 분 단위 , 0.1: 10분, 0.5:30분, 1:1시간, ... , 24:1일
        @SerializedName("price")
        private String price; //가격
        @SerializedName("reg_id")
        private String reg_id; //등록자
        @SerializedName("reg_date")
        private String reg_date; //등록일
        /*@SerializedName("update_id")
        private String update_id; //수정자 (안써도 무방하나 수정시 최종 수정자를 남기려면 사용하세요)
        @SerializedName("update_date")
        private Timestamp update_date; //수정일 (안써도 무방하나 수정시 최종 수정자를 남기려면 사용하세요)*/

        //건물 주소
        @SerializedName("b_name")
        private String b_name; //건물이름
        @SerializedName("b_area1")
        private String b_area1; //주소1 ex)서울시
        @SerializedName("b_area2")
        private String b_area2; //주소2 ex)금천구
        @SerializedName("b_address")
        private String b_address; //상세 주소 ex) 가산동 654-5

        public String getP_type() {
            return p_type;
        }

        public void setP_type(String p_type) {
            this.p_type = p_type;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public String getP_code() {
            return p_code;
        }

        public void setP_code(String p_code) {
            this.p_code = p_code;
        }

        public String getB_code() {
            return b_code;
        }

        public void setB_code(String b_code) {
            this.b_code = b_code;
        }

        public String getHourly() {
            return hourly;
        }

        public void setHourly(String hourly) {
            this.hourly = hourly;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public void String(String reg_date) {
            this.reg_date = reg_date;
        }


        public String getB_name() {
            return b_name;
        }

        public void setB_name(String b_name) {
            this.b_name = b_name;
        }

        public String getB_area1() {
            return b_area1;
        }

        public void setB_area1(String b_area1) {
            this.b_area1 = b_area1;
        }

        public String getB_area2() {
            return b_area2;
        }

        public void setB_area2(String b_area2) {
            this.b_area2 = b_area2;
        }

        public String getB_address() {
            return b_address;
        }

        public void setB_address(String b_address) {
            this.b_address = b_address;
        }
    }
}
