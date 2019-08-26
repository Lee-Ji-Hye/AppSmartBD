package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodDetailVO extends BaseRespon  {


    @SerializedName("menus")
    private ArrayList<Menus> menus; //메뉴정보

    public FoodDetailVO() {
        menus = new ArrayList<>();
    }


    public ArrayList<Menus> getMenuList() {
        return menus;
    }


    //메뉴
    public class Menus {
        @SerializedName("f_code")
        private String fcode;

        @SerializedName("comp_seq")
        private String comp_seq;

        @SerializedName("comp_org")
        private String comp_org;

        @SerializedName("f_category")
        private String category;

        @SerializedName("f_name")
        private String name;

        @SerializedName("f_desc")
        private String subname; //메뉴간단 한줄소개

        @SerializedName("f_price")
        private String price;

        @SerializedName("f_takeout")
        private String takeout;

        @SerializedName("f_takeout_sale")
        private String takeout_sale;

        @SerializedName("userid")
        private String userid;

        @SerializedName("f_icon")
        private String icon;

        @SerializedName("f_regidate")
        private String regidate;

        @SerializedName("f_img")
        private String f_img;


        /* getter & setter*/
        public String getFcode() {
            return fcode;
        }

        public void setFcode(String fcode) {
            this.fcode = fcode;
        }

        public String getComp_seq() {
            return comp_seq;
        }

        public void setComp_seq(String comp_seq) {
            this.comp_seq = comp_seq;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTakeout() {
            return takeout;
        }

        public String getComp_org() {
            return comp_org;
        }

        public void setComp_org(String comp_org) {
            this.comp_org = comp_org;
        }

        public void setTakeout(String takeout) {
            this.takeout = takeout;
        }

        public String getTakeout_sale() {
            return takeout_sale;
        }

        public void setTakeout_sale(String takeout_sale) {
            this.takeout_sale = takeout_sale;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getRegidate() {
            return regidate;
        }

        public void setRegidate(String regidate) {
            this.regidate = regidate;
        }

        public String getPrice() {
            return price;
        }

        public String getName() {
            return name;
        }

        public String getSubname() {
            return subname;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setSubname(String subname) {
            this.subname = subname;
        }

        public String getF_img() {
            return f_img;
        }

        public void setF_img(String f_img) {
            this.f_img = f_img;
        }
    }



}
