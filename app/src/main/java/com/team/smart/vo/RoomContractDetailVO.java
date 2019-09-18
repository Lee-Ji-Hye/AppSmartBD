package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RoomContractDetailVO extends BaseRespon {

    @SerializedName("contracts")
    private ArrayList<Contract> contracts; //계약 정보

    public RoomContractDetailVO() {
        contracts = new ArrayList<>();
    }

    public ArrayList<Contract> getContractList() {
        return contracts;
    }

    public class Contract {

        @SerializedName("b_area1")
        private String b_area1;        //서울시
        @SerializedName("b_area2")
        private String b_area2;        //금천구
        @SerializedName("b_address")
        private String b_address;      //주소
        @SerializedName("b_year")
        private String b_year;         //준공년도
        @SerializedName("b_landarea")
        private String b_landarea;     //대지면적
        @SerializedName("b_buildarea")
        private String b_buildarea;    //건축면적
        @SerializedName("b_buildscale")
        private String b_buildscale;   //건축규모
        @SerializedName("r_type")
        private String r_type;         //거래 타입
        @SerializedName("r_price")
        private int r_price;           //매물 가격
        @SerializedName("r_deposit")
        private int r_deposit;         //보증금
        @SerializedName("r_premium")
        private int r_premium;         //권리금(상가)
        @SerializedName("rt_hash")
        private String rt_hash;        //계약 해쉬코드
        @SerializedName("r_code")
        private String r_code;         //매물 코드
        @SerializedName("r_blockcode")
        private int r_blockcode;       //매물 블록체인 코드
        @SerializedName("r_floor")
        private int r_floor;           //해당층수
        @SerializedName("r_kind")
        private String r_kind;         //매물 종류
        @SerializedName("regidate")
        private long regidate;    //등록일

        @SerializedName("rt_state")
        private String rt_state;       //계약 상태
        @SerializedName("rt_mobile")
        private String rt_mobile;      //임차인 휴대폰
        @SerializedName("rt_email")
        private String rt_email;       //임차인 이메일
        @SerializedName("rt_date")
        private String rt_date;       //계약 날짜
        @SerializedName("rt_date2")
        private String rt_date2;       //계약 만기일
        @SerializedName("rt_deposit")
        private String rt_deposit;     //계약 보증금
        @SerializedName("staff_id")
        private String staff_id;       //관리자 아이디
        @SerializedName("name")
        private String name;           //이름
        @SerializedName("email")
        private String email;          //이메일
        @SerializedName("hp")
        private String hp;             //핸드폰

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

        public String getB_year() {
            return b_year;
        }

        public void setB_year(String b_year) {
            this.b_year = b_year;
        }

        public String getB_landarea() {
            return b_landarea;
        }

        public void setB_landarea(String b_landarea) {
            this.b_landarea = b_landarea;
        }

        public String getB_buildarea() {
            return b_buildarea;
        }

        public void setB_buildarea(String b_buildarea) {
            this.b_buildarea = b_buildarea;
        }

        public String getB_buildscale() {
            return b_buildscale;
        }

        public void setB_buildscale(String b_buildscale) {
            this.b_buildscale = b_buildscale;
        }

        public String getR_type() {
            return r_type;
        }

        public void setR_type(String r_type) {
            this.r_type = r_type;
        }

        public int getR_price() {
            return r_price;
        }

        public void setR_price(int r_price) {
            this.r_price = r_price;
        }

        public int getR_deposit() {
            return r_deposit;
        }

        public void setR_deposit(int r_deposit) {
            this.r_deposit = r_deposit;
        }

        public int getR_premium() {
            return r_premium;
        }

        public void setR_premium(int r_premium) {
            this.r_premium = r_premium;
        }

        public String getRt_hash() {
            return rt_hash;
        }

        public void setRt_hash(String rt_hash) {
            this.rt_hash = rt_hash;
        }

        public String getR_code() {
            return r_code;
        }

        public void setR_code(String r_code) {
            this.r_code = r_code;
        }

        public int getR_blockcode() {
            return r_blockcode;
        }

        public void setR_blockcode(int r_blockcode) {
            this.r_blockcode = r_blockcode;
        }

        public int getR_floor() {
            return r_floor;
        }

        public void setR_floor(int r_floor) {
            this.r_floor = r_floor;
        }

        public String getR_kind() {
            return r_kind;
        }

        public void setR_kind(String r_kind) {
            this.r_kind = r_kind;
        }

        public long getRegidate() {
            return regidate;
        }

        public void setRegidate(long regidate) {
            this.regidate = regidate;
        }

        public String getRt_state() {
            return rt_state;
        }

        public void setRt_state(String rt_state) {
            this.rt_state = rt_state;
        }

        public String getRt_mobile() {
            return rt_mobile;
        }

        public void setRt_mobile(String rt_mobile) {
            this.rt_mobile = rt_mobile;
        }

        public String getRt_email() {
            return rt_email;
        }

        public void setRt_email(String rt_email) {
            this.rt_email = rt_email;
        }

        public String getRt_date() {
            return rt_date;
        }

        public void setRt_date(String rt_date) {
            this.rt_date = rt_date;
        }

        public String getRt_date2() {
            return rt_date2;
        }

        public void setRt_date2(String rt_date2) {
            this.rt_date2 = rt_date2;
        }

        public String getRt_deposit() {
            return rt_deposit;
        }

        public void setRt_deposit(String rt_deposit) {
            this.rt_deposit = rt_deposit;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
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
    }
}
