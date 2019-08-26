package com.team.smart.network;

import com.team.smart.vo.FoodDetailVO;
import com.team.smart.vo.FoodStoreVO;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.Foods;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface
{
    //protocall 약속
    //@GET("/smart/api/food/getList")
    //Call<Foods> DishesList(@Query("page") HashMap<String,String> param);
    //주차관리 은지 시작
    //주차장 건물 전체리스트 정보
    @GET("/smart/api/parking/ParkingInfo")
    Call<ParkingBDVO> getParkingInfo();

    //주차장 건물 정보 상세
    @GET("/smart/api/parking/ParkingbuidingInfo")
    Call<ParkingBDVO> getParkingBDInfo(@Query("b_code") String b_code);
    //주차관리 은지 종료

    //지혜 시작
    @GET("/smart/api/food/getStoreList")
    Call<FoodStoreVO> DishesList(@Query("f_category") String f_category, @Query("comp_seq") String comp_seq);
    @GET("/smart/api/food/getStoreList")
    Call<FoodStoreVO> foodStore(@Query("comp_seq") String comp_seq);
    @GET("/smart/api/food/getFoodMenuList")
    Call<FoodDetailVO> foodMenuList(@Query("comp_seq") String comp_seq);
    //지혜 종료

}
