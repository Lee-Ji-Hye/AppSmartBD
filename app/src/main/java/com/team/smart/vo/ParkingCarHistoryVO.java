package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

//주차권 상품 VO -양은지
public class ParkingCarHistoryVO extends BaseRespon {

    @SerializedName("carHistories")
    private ArrayList<ParkingCarHistoryVO.carHistory> carHistories; //메뉴정보
    public ParkingCarHistoryVO() {
        carHistories = new ArrayList<>();
    }
    public ArrayList<ParkingCarHistoryVO.carHistory> getCarHistories() {
        return carHistories;
    }

    public class carHistory {
        @SerializedName("inoutcode")
        private String inoutcode; //입출차코드
        @SerializedName("car_number")
        private String car_number; // 차번호
        @SerializedName("car_number_img")
        private String car_number_img; // 차번호이미지
        @SerializedName("b_code")
        private String b_code; // 건물코드
        @SerializedName("in_time")
        private String in_time; // 입차시간
        @SerializedName("parking_time")
        private String parking_time; // 주차시간
        @SerializedName("out_time")
        private String out_time; // 출차시간
        @SerializedName("parking_location")
        private String parking_location; // 주차위치
        @SerializedName("parking_state")
        private char parking_state; // 출차전이면 0, 출차후면 1
        @SerializedName("b_name")
        private String b_name;//주차장 이름
        @SerializedName("ip")
        private String ip;

        public String getInoutcode() {
            return inoutcode;
        }

        public void setInoutcode(String inoutcode) {
            this.inoutcode = inoutcode;
        }

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getCar_number_img() {
            return car_number_img;
        }

        public void setCar_number_img(String car_number_img) {
            this.car_number_img = car_number_img;
        }

        public String getB_code() {
            return b_code;
        }

        public void setB_code(String b_code) {
            this.b_code = b_code;
        }

        public String getIn_time() {
            return in_time;
        }

        public void setIn_time(String in_time) {
            this.in_time = in_time;
        }

        public String getParking_time() {
            return parking_time;
        }

        public void setParking_time(String parking_time) {
            this.parking_time = parking_time;
        }

        public String getOut_time() {
            return out_time;
        }

        public void setOut_time(String out_time) {
            this.out_time = out_time;
        }

        public String getParking_location() {
            return parking_location;
        }

        public void setParking_location(String parking_location) {
            this.parking_location = parking_location;
        }

        public char getParking_state() {
            return parking_state;
        }

        public void setParking_state(char parking_state) {
            this.parking_state = parking_state;
        }

        public String getB_name() {
            return b_name;
        }

        public void setB_name(String b_name) {
            this.b_name = b_name;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
