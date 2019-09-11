package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodOrderDetailVO implements Serializable {

    @SerializedName("orderDetail")
    private ArrayList<FoodOrderDetailVO.Detail> orderDetail; //메뉴정보

    public FoodOrderDetailVO() {
        orderDetail = new ArrayList<>();
    }


    public ArrayList<FoodOrderDetailVO.Detail> getOrderDetail() {
        return orderDetail;
    }

    public class Detail {
        @SerializedName("f_ocode")
        private String f_ocode;
        @SerializedName("f_status")
        private String f_status;
        @SerializedName("comp_seq")
        private String comp_seq;         //업체코드
        @SerializedName("f_name")
        private String f_name;           //이름
        @SerializedName("f_hp")
        private String f_hp;             //핸드폰번호
        @SerializedName("f_receive_time")
        private String f_receive_time;  //예상수령시간
        @SerializedName("f_message")
        private String f_message;       //요청사항
        @SerializedName("f_person_num")
        private String f_person_num;    //인원
        @SerializedName("userid")
        private String userid;          //아이디
        @SerializedName("f_serial")
        private String f_serial;        //시리얼번호
        @SerializedName("f_amount")
        private String f_amount;        //주문 금액
        @SerializedName("f_sale_price")
        private String f_sale_price;    //할인 금액(쿠폰)
        @SerializedName("f_pay_type")
        private String f_pay_type;      //결제수단
        @SerializedName("f_pay_price")
        private String f_pay_price;     //결제한 금액
        //private String f_refund_price;  //환불금액
        @SerializedName("f_rate")
        private String f_rate;          //수수료
        @SerializedName("f_regidate")
        private String f_regidate;      //등록일


        @SerializedName("menulist")
        private ArrayList<FoodCartVO> menulist;

        //그냥.. 업체 이름이랑 업체 전화번호도 같이 가져오자..
        private String comp_org;
        private String comp_hp;

        public String getF_ocode() {
            return f_ocode;
        }

        public void setF_ocode(String f_ocode) {
            this.f_ocode = f_ocode;
        }

        public String getF_status() {
            return f_status;
        }

        public void setF_status(String f_status) {
            this.f_status = f_status;
        }

        public String getComp_org() {
            return comp_org;
        }

        public void setComp_org(String comp_org) {
            this.comp_org = comp_org;
        }

        public String getComp_hp() {
            return comp_hp;
        }

        public void setComp_hp(String comp_hp) {
            this.comp_hp = comp_hp;
        }

        public ArrayList<FoodCartVO> getMenulist() {
            return menulist;
        }

        public void setMenulist(ArrayList<FoodCartVO> menulist) {
            this.menulist = menulist;
        }

        public String getComp_seq() {
            return comp_seq;
        }

        public void setComp_seq(String comp_seq) {
            this.comp_seq = comp_seq;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getF_hp() {
            return f_hp;
        }

        public void setF_hp(String f_hp) {
            this.f_hp = f_hp;
        }

        public String getF_receive_time() {
            return f_receive_time;
        }

        public void setF_receive_time(String f_receive_time) {
            this.f_receive_time = f_receive_time;
        }

        public String getF_message() {
            return f_message;
        }

        public void setF_message(String f_message) {
            this.f_message = f_message;
        }

        public String getF_person_num() {
            return f_person_num;
        }

        public void setF_person_num(String f_person_num) {
            this.f_person_num = f_person_num;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getF_serial() {
            return f_serial;
        }

        public void setF_serial(String f_serial) {
            this.f_serial = f_serial;
        }

        public String getF_amount() {
            return f_amount;
        }

        public void setF_amount(String f_amount) {
            this.f_amount = f_amount;
        }

        public String getF_sale_price() {
            return f_sale_price;
        }

        public void setF_sale_price(String f_sale_price) {
            this.f_sale_price = f_sale_price;
        }

        public String getF_pay_type() {
            return f_pay_type;
        }

        public void setF_pay_type(String f_pay_type) {
            this.f_pay_type = f_pay_type;
        }

        public String getF_pay_price() {
            return f_pay_price;
        }

        public void setF_pay_price(String f_pay_price) {
            this.f_pay_price = f_pay_price;
        }

        public String getF_rate() {
            return f_rate;
        }

        public void setF_rate(String f_rate) {
            this.f_rate = f_rate;
        }

        public String getF_regidate() {
            return f_regidate;
        }

        public void setF_regidate(String f_regidate) {
            this.f_regidate = f_regidate;
        }
    }
}
