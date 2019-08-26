package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Rooms extends BaseRespon {

    @SerializedName("rooms")
    private ArrayList<Room> rooms;

    public Rooms()
    {
        rooms = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() { return rooms; }

    public static class Room {

        @SerializedName("r_code")
        private String r_code;         //매물 코드
        @SerializedName("b_code")
        private String b_code;         //건물 코드
        @SerializedName("r_img")
        private String r_img;          //매물 사진
        @SerializedName("r_name")
        private String r_name;         //매물명
        @SerializedName("r_type")
        private String r_type;         //거래 타입
        @SerializedName("r_price")
        private String r_price;        //매물 가격
        @SerializedName("r_reposit")
        private String r_reposit;      //보증금
        @SerializedName("r_premium")
        private String r_premium;      //권리금(상가)
        @SerializedName("r_ofer_fee")
        private String r_ofer_fee;     //관리비
        @SerializedName("r_floor")
        private String r_floor;        //해당층수
        @SerializedName("r_area")
        private String r_area;         //면적
        @SerializedName("r_indi_space")
        private String r_indi_space;   //독립공간(회의실,탕비실 등) 유무
        @SerializedName("r_able_date")
        private String r_able_date;    //입주가능일
        @SerializedName("r_toilet")
        private String r_toilet;       //화장실
        @SerializedName("r_desc")
        private String r_desc;         //상세설명
        @SerializedName("r_pmemo")
        private String r_pmemo;        //비공개메모 (선택)
        @SerializedName("regidate")
        private String regidate;       //등록일
        @SerializedName("r_delete")
        private String r_delete;       //공개 여부
        @SerializedName("userid")
        private String userid;         //관리자 아이디
        @SerializedName("r_kind")
        private String r_kind;         //매물 종류

        public String getR_code() {
            return r_code;
        }

        public void setR_code(String r_code) {
            this.r_code = r_code;
        }

        public String getB_code() {
            return b_code;
        }

        public void setB_code(String b_code) {
            this.b_code = b_code;
        }

        public String getR_img() {
            return r_img;
        }

        public void setR_img(String r_img) {
            this.r_img = r_img;
        }

        public String getR_name() {
            return r_name;
        }

        public void setR_name(String r_name) {
            this.r_name = r_name;
        }

        public String getR_type() {
            return r_type;
        }

        public void setR_type(String r_type) {
            this.r_type = r_type;
        }

        public String getR_price() {
            return r_price;
        }

        public void setR_price(String r_price) {
            this.r_price = r_price;
        }

        public String getR_reposit() {
            return r_reposit;
        }

        public void setR_reposit(String r_reposit) {
            this.r_reposit = r_reposit;
        }

        public String getR_premium() {
            return r_premium;
        }

        public void setR_premium(String r_premium) {
            this.r_premium = r_premium;
        }

        public String getR_ofer_fee() {
            return r_ofer_fee;
        }

        public void setR_ofer_fee(String r_ofer_fee) {
            this.r_ofer_fee = r_ofer_fee;
        }

        public String getR_floor() {
            return r_floor;
        }

        public void setR_floor(String r_floor) {
            this.r_floor = r_floor;
        }

        public String getR_area() {
            return r_area;
        }

        public void setR_area(String r_area) {
            this.r_area = r_area;
        }

        public String getR_indi_space() {
            return r_indi_space;
        }

        public void setR_indi_space(String r_indi_space) {
            this.r_indi_space = r_indi_space;
        }

        public String getR_able_date() {
            return r_able_date;
        }

        public void setR_able_date(String r_able_date) {
            this.r_able_date = r_able_date;
        }

        public String getR_toilet() {
            return r_toilet;
        }

        public void setR_toilet(String r_toilet) {
            this.r_toilet = r_toilet;
        }

        public String getR_desc() {
            return r_desc;
        }

        public void setR_desc(String r_desc) {
            this.r_desc = r_desc;
        }

        public String getR_pmemo() {
            return r_pmemo;
        }

        public void setR_pmemo(String r_pmemo) {
            this.r_pmemo = r_pmemo;
        }

        public String getRegidate() {
            return regidate;
        }

        public void setRegidate(String regidate) {
            this.regidate = regidate;
        }

        public String getR_delete() {
            return r_delete;
        }

        public void setR_delete(String r_delete) {
            this.r_delete = r_delete;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getR_kind() {
            return r_kind;
        }

        public void setR_kind(String r_kind) {
            this.r_kind = r_kind;
        }

    }

}
