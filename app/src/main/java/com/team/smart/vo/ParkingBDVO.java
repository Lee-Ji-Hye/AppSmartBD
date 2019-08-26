package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParkingBDVO extends BaseRespon {

    @SerializedName("parkingDBs")
    private ArrayList<Parking> parkingDBs; //메뉴정보

    public ParkingBDVO() {
        parkingDBs = new ArrayList<>();
    }

    public ArrayList<Parking> getParkingBDsList() {
        return parkingDBs;
    }

    public class Parking {
        //데이터
        @SerializedName("b_code")
        private String b_code; // 건물코드
        @SerializedName("b_name")
        private String b_name; // 건물명
        @SerializedName("p_floor")
        private int p_floor; //층수
        @SerializedName("able_position")
        private int able_position; // 총 주차자리
        @SerializedName("able_height")
        private int able_height; // 출차높이제한(m)
        @SerializedName("lat")
        private double lat;	//위도
        @SerializedName("lon")
        private double lon; //경도
        @SerializedName("operate_time_day")
        private String operate_time_day; //평일영업시간
        @SerializedName("operate_time_week")
        private String operate_time_week; //공휴일영업시간
        @SerializedName("operate_tel")
        private String operate_tel; //주차관리사무소번호

        @SerializedName("b_area1")
        private String b_area1; //주소1 ex)서울시
        @SerializedName("b_area2")
        private String b_area2; //주소2 ex)금천구
        @SerializedName("b_address")
        private String b_address; //상세 주소 ex) 가산동 654-5

        @SerializedName("p_seq")
        private int p_seq; // 상세정보 시퀀스
        @SerializedName("p_detail_floor")
        private String p_detail_floor; //층 (B1, B2,,)
        @SerializedName("able_position_floor")
        private int able_position_floor; // 층별 주차가능자리수
        @SerializedName("disable_position")
        private int disable_position; // 장애인구역
        @SerializedName("reserved_position")
        private int reserved_position; //-- 지정주차구역
        @SerializedName("ask")
        private String ask; //-- 기타사항

        //getter,setter

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

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public String getOperate_time_day() {
            return operate_time_day;
        }

        public void setOperate_time_day(String operate_time_day) {
            this.operate_time_day = operate_time_day;
        }

        public String getOperate_time_week() {
            return operate_time_week;
        }

        public void setOperate_time_week(String operate_time_week) {
            this.operate_time_week = operate_time_week;
        }

        public String getOperate_tel() {
            return operate_tel;
        }

        public void setOperate_tel(String operate_tel) {
            this.operate_tel = operate_tel;
        }

        public String getB_code() {
            return b_code;
        }

        public void setB_code(String b_code) {
            this.b_code = b_code;
        }

        public String getB_name() {
            return b_name;
        }

        public void setB_name(String b_name) {
            this.b_name = b_name;
        }

        public int getP_floor() {
            return p_floor;
        }

        public void setP_floor(int p_floor) {
            this.p_floor = p_floor;
        }

        public int getAble_position() {
            return able_position;
        }

        public void setAble_position(int able_position) {
            this.able_position = able_position;
        }

        public int getAble_height() {
            return able_height;
        }

        public void setAble_height(int able_height) {
            this.able_height = able_height;
        }

        public int getP_seq() {
            return p_seq;
        }

        public void setP_seq(int p_seq) {
            this.p_seq = p_seq;
        }

        public String getP_detail_floor() {
            return p_detail_floor;
        }

        public void setP_detail_floor(String p_detail_floor) {
            this.p_detail_floor = p_detail_floor;
        }

        public int getAble_position_floor() {
            return able_position_floor;
        }

        public void setAble_position_floor(int able_position_floor) {
            this.able_position_floor = able_position_floor;
        }

        public int getDisable_position() {
            return disable_position;
        }

        public void setDisable_position(int disable_position) {
            this.disable_position = disable_position;
        }

        public int getReserved_position() {
            return reserved_position;
        }

        public void setReserved_position(int reserved_position) {
            this.reserved_position = reserved_position;
        }

        public String getAsk() {
            return ask;
        }

        public void setAsk(String ask) {
            this.ask = ask;
        }
    }
}
