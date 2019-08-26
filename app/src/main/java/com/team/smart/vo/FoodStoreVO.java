package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FoodStoreVO extends BaseRespon {
    @SerializedName("stores")
    private ArrayList<FoodStoreVO.Store> stores;

    public FoodStoreVO()
    {
        stores = new ArrayList<>();
    }

    public ArrayList<FoodStoreVO.Store> getStores() {
        return stores;
    }

    public class Store {
        @SerializedName("f_seq")
        private String f_seq;       //시퀀스
        @SerializedName("comp_seq")
        private String comp_seq;    //업체번호
        @SerializedName("long_desc")
        private String long_desc;   //상세소개
        @SerializedName("short_desc")
        private String short_desc;  //한줄소개
        @SerializedName("f_category")
        private String f_category;  //카테고리 |* DB에 ENUM걸어놔서 한식, 중식, 일식, 디저트 이외의 카테고리가 들어가면 오류남. 추가하고 싶을경우 알려주세요*|
        @SerializedName("f_mainimg")
        private String f_mainimg;   //업체이미지

        @SerializedName("comp_org")
        private String comp_org;    //업체명
        @SerializedName("comp_branch")
        private String comp_branch; //업체주소
        @SerializedName("comp_section")
        private String comp_section;//업체분류
        @SerializedName("comp_hp")
        private String comp_hp;     //업체전화번호
        @SerializedName("f_coupon_num")

        private String f_coupon_num; //쿠폰명
        @SerializedName("f_coupon_name")
        private String f_coupon_name;//쿠폰네임
        @SerializedName("f_coupon_price")
        private String f_coupon_price;//쿠폰금액

        @SerializedName("star")
        private String star;//별점
        @SerializedName("reviewCnt")
        private String reviewCnt;//리뷰 수


        public String getF_seq() {
            return f_seq;
        }

        public void setF_seq(String f_seq) {
            this.f_seq = f_seq;
        }

        public String getComp_seq() {
            return comp_seq;
        }

        public void setComp_seq(String comp_seq) {
            this.comp_seq = comp_seq;
        }

        public String getLong_desc() {
            return long_desc;
        }

        public void setLong_desc(String long_desc) {
            this.long_desc = long_desc;
        }

        public String getShort_desc() {
            return short_desc;
        }

        public void setShort_desc(String short_desc) {
            this.short_desc = short_desc;
        }

        public String getF_category() {
            return f_category;
        }

        public void setF_category(String f_category) {
            this.f_category = f_category;
        }

        public String getF_mainimg() {
            return f_mainimg;
        }

        public void setF_mainimg(String f_mainimg) {
            this.f_mainimg = f_mainimg;
        }

        public String getComp_org() {
            return comp_org;
        }

        public void setComp_org(String comp_org) {
            this.comp_org = comp_org;
        }

        public String getComp_section() {
            return comp_section;
        }

        public void setComp_section(String comp_section) {
            this.comp_section = comp_section;
        }

        public String getComp_hp() {
            return comp_hp;
        }

        public void setComp_hp(String comp_hp) {
            this.comp_hp = comp_hp;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getReviewCnt() {
            return reviewCnt;
        }

        public void setReviewCnt(String reviewCnt) {
            this.reviewCnt = reviewCnt;
        }

        public String getComp_branch() {
            return comp_branch;
        }

        public void setComp_branch(String comp_branch) {
            this.comp_branch = comp_branch;
        }

        public String getF_coupon_num() {
            return f_coupon_num;
        }

        public void setF_coupon_num(String f_coupon_num) {
            this.f_coupon_num = f_coupon_num;
        }

        public String getF_coupon_name() {
            return f_coupon_name;
        }

        public void setF_coupon_name(String f_coupon_name) {
            this.f_coupon_name = f_coupon_name;
        }

        public String getF_coupon_price() {
            return f_coupon_price;
        }

        public void setF_coupon_price(String f_coupon_price) {
            this.f_coupon_price = f_coupon_price;
        }
    }
}
