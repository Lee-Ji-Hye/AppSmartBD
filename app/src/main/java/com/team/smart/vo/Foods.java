package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Foods extends BaseRespon {
    @SerializedName("stores")
    private ArrayList<Food> foods;

    public Foods()
    {
        foods = new ArrayList<>();
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    //데이타
    public static class Food{
        @SerializedName("f_code")
        private String f_code;		 //메뉴코드
        @SerializedName("comp_seq")
        private String comp_seq;	 //업체코드
        @SerializedName("f_catagory")
        private String f_catagory;	 //카테고리
        @SerializedName("f_name")
        private String f_name;		 //메뉴명
        @SerializedName("f_price")
        private String f_price;		 //메뉴가격
        @SerializedName("f_takeout")
        private String f_takeout;	 //테이크아웃 유무(Y,N)
        @SerializedName("f_takeout_sale")
        private String f_takeout_sale;  //테이크아웃 할인가격
        @SerializedName("f_img")
        private String f_img;		 //이미지
        @SerializedName("userid")
        private String userid;		 //등록자 아이디
        @SerializedName("f_icon")
        private String f_icon;		 //메뉴아이콘(NEW, HOT, BEST 등)
        @SerializedName("f_regidate")
        private Timestamp f_regidate;//등록일

        @SerializedName("star")
        private String star;//별점
        @SerializedName("comp_org")
        private String comp_org ="";//업장 명
        @SerializedName("short_desc")
        private String short_desc ="아주마싯어용";//업장 한줄 소개
        @SerializedName("reviewCnt")
        private String reviewCnt;//리뷰 수
        @SerializedName("f_mainimg")
        private String f_mainimg;

        public String getF_mainimg() {
            return f_mainimg;
        }

        public void setF_mainimg(String f_mainimg) {
            this.f_mainimg = f_mainimg;
        }

        public String getF_code() {
            return f_code;
        }

        public void setF_code(String f_code) {
            this.f_code = f_code;
        }

        public String getComp_seq() {
            return comp_seq;
        }

        public void setComp_seq(String comp_seq) {
            this.comp_seq = comp_seq;
        }

        public String getF_catagory() {
            return f_catagory;
        }

        public void setF_catagory(String f_catagory) {
            this.f_catagory = f_catagory;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }

        public String getF_price() {
            return f_price;
        }

        public void setF_price(String f_price) {
            this.f_price = f_price;
        }

        public String getF_takeout() {
            return f_takeout;
        }

        public void setF_takeout(String f_takeout) {
            this.f_takeout = f_takeout;
        }

        public String getF_takeout_sale() {
            return f_takeout_sale;
        }

        public void setF_takeout_sale(String f_takeout_sale) {
            this.f_takeout_sale = f_takeout_sale;
        }

        public String getF_img() {
            return f_img;
        }

        public void setF_img(String f_img) {
            this.f_img = f_img;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getF_icon() {
            return f_icon;
        }

        public void setF_icon(String f_icon) {
            this.f_icon = f_icon;
        }

        public Timestamp getF_regidate() {
            return f_regidate;
        }

        public void setF_regidate(Timestamp f_regidate) {
            this.f_regidate = f_regidate;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getComp_org() {
            return comp_org;
        }

        public void setComp_org(String comp_org) {
            this.comp_org = comp_org;
        }

        public String getShort_desc() {
            return short_desc;
        }

        public void setShort_desc(String short_desc) {
            this.short_desc = short_desc;
        }

        public String getReviewCnt() {
            return reviewCnt;
        }

        public void setReviewCnt(String reviewCnt) {
            this.reviewCnt = reviewCnt;
        }
    }

}
