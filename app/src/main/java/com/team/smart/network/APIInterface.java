package com.team.smart.network;

import com.team.smart.vo.FoodDetailVO;
import com.team.smart.vo.FoodStoreVO;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.Foods;
import com.team.smart.vo.ParkingOrderVO;
import com.team.smart.vo.ParkingTicketVO;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface
{
    //protocall 약속
    //@GET("/smart/api/food/getList")
    //Call<Foods> DishesList(@Query("page") HashMap<String,String> param);

    //주차관리 은지 시작
    //주차장 건물 전체리스트 정보
    @GET("/api/parking/ParkingInfo")
    Call<ParkingBDVO> getParkingInfo();
    //주차장 건물 정보 상세
    @GET("/api/parking/ParkingbuidingInfo")
    Call<ParkingBDVO> getParkingBDInfo(@Query("b_code") String b_code);
    //주차권 정보 가져오기
    @GET("/api/parking/ParkingTicketInfo")
    Call<ParkingTicketVO> getParkingTicketInfo(@Query("b_code") String b_code);
    //결제시 하나의 주차권 정보 가져오기
    @GET("/api/parking/ParkingTicketOne")
    Call<ParkingTicketVO> getParkingTicketOne(@Query("p_code") String p_code);
    //주차관리 은지 종료

    //지혜 시작
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> DishesList(@Query("f_category") String f_category, @Query("comp_seq") String comp_seq);
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> foodStore(@Query("comp_seq") String comp_seq);
    @GET("/api/food/getFoodMenuList")
    Call<FoodDetailVO> foodMenuList(@Query("comp_seq") String comp_seq);
    //지혜 종료

    //카카오페이
    //@POST("/kakao/payTest")
    //Call<HashMap> kakaoPayParkingTicket(@Body FoodOrderVO foodOrderVO);
    @POST("/api/parking/kakao/ParkingBuyTicket")
    Call<HashMap> ParkingBuyTicket(@Body ParkingOrderVO orderVO);
    @POST
    Call<HashMap> kakaoPaySuccess(@Url String reUrl, @Query("p_ocode") String p_ocode);
    //카카오페이 끝
}
