package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodOrderListVO extends BaseRespon  {


    @SerializedName("orderList")
    private ArrayList<List> list; //메뉴정보

    public FoodOrderListVO() {
        list = new ArrayList<>();
    }


    public ArrayList<List> getOrderList() {
        return list;
    }


    //메뉴
    public class List {
        @SerializedName("f_ocode")
        private String f_ocode;
        @SerializedName("f_name")
        private String f_name;
        @SerializedName("comp_org")
        private String comp_org;
        @SerializedName("comp_hp")
        private String comp_hp;
        @SerializedName("f_status")
        private String f_status;
        @SerializedName("f_regidate")
        private String f_regidate;

        public String getF_ocode() {
            return f_ocode;
        }

        public void setF_ocode(String f_ocode) {
            this.f_ocode = f_ocode;
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

        public String getF_status() {
            return f_status;
        }

        public void setF_status(String f_status) {
            this.f_status = f_status;
        }

        public String getF_regidate() {
            return f_regidate;
        }

        public void setF_regidate(String f_regidate) {
            this.f_regidate = f_regidate;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }
    }



}
