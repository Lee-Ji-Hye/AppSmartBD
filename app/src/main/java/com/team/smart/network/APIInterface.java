package com.team.smart.network;

import com.team.smart.vo.FoodCouponVO;
import com.team.smart.vo.FoodDetailVO;
import com.team.smart.vo.FoodOrderDetailVO;
import com.team.smart.vo.FoodOrderListVO;
import com.team.smart.vo.FoodOrderVO;
import com.team.smart.vo.FoodStoreVO;
import com.team.smart.vo.ParkingBDVO;
import com.team.smart.vo.ParkingBasicOrderVO;
import com.team.smart.vo.ParkingBasicVO;
import com.team.smart.vo.ParkingCarHistoryVO;
import com.team.smart.vo.ParkingOrderVO;
import com.team.smart.vo.ParkingPayOrderDetailVO;
import com.team.smart.vo.ParkingTicketVO;
import com.team.smart.vo.RequestUserVO;
import com.team.smart.vo.RoomBVO;
import com.team.smart.vo.UserCarVO;
import com.team.smart.vo.RoomContractDetailVO;
import com.team.smart.vo.RoomContractVO;

import com.team.smart.vo.UserVO;

import java.util.HashMap;
import java.util.Map;

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
    //티켓구매하기
    @POST("/api/parking/kakao/ParkingBuyTicket")
    Call<HashMap> ParkingBuyTicket(@Body ParkingOrderVO orderVO);
    //유저정보가져오기
    @POST("/api/parking/getUserInfo")
    Call<UserCarVO> getUserInfo(@Query("userid") String userid);
    //회원차량정보 입력
    @POST("/api/parking/insertUserCarInfo")
    Call<HashMap> insertUserCarInfo(@Body UserCarVO carVO);
    //차량정보 가져오기
    @POST("/api/parking/getUserCarInfo")
    Call<UserCarVO> getUserCarInfo(@Query("userid") String userid);
    //차량정보 삭제
    @POST("/api/parking/delUserCarInfo")
    Call<HashMap> delUserCarInfo(@Query("userid") String userid);
    //입출차 정보 가져오기
    @POST("/api/parking/getCarHistory")
    Call<ParkingCarHistoryVO> getCarHistory(@Query("carnum") String carnum);
    //입출차 정보 가져오기
    @POST("/api/parking/getCarHistory2")
    Call<ParkingCarHistoryVO> getCarHistory2(@Query("inoutcode") String inoutcode);
    //주차요금 가져오기
    @POST("/api/parking/getParkingPrice")
    Call<ParkingBasicVO> getParkingPrice(@Query("b_code") String b_code);
    //결제 정보 가져 오기
    @POST("/api/parking/getPayTime")
    Call<ParkingPayOrderDetailVO> getPayTime(@Query("inoutcode") String inoutcode);
    //주차요금 결제 하기
    @POST("/api/parking/kakao/ParkingPayBasic")
    Call<HashMap> ParkingPayBasic(@Body ParkingBasicOrderVO orderVO);
    //아이디와 건물코드로 주차권 정보 가져오기
    @POST("/api/parking/kakao/getBDUserTicketList")
    Call<HashMap> getBDUserTicketList(@Body ParkingBasicOrderVO orderVO);
    //주차관리 은지 종료

    //지혜 시작
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> DishesList(@Query("f_category") String f_category, @Query("comp_seq") String comp_seq);
    @GET("/api/food/getStoreList")
    Call<FoodStoreVO> foodStore(@Query("comp_seq") String comp_seq);
    @GET("/api/food/getFoodMenuList")
    Call<FoodDetailVO> foodMenuList(@Query("comp_seq") String comp_seq);
    @POST("/api/food/getBeachonCoupon")
    Call<FoodCouponVO> getBeaconCouponList(@Body HashMap map);
    @POST("/api/food/payTest")
    Call<HashMap> kakaoPayTest(@Body FoodOrderVO foodOrderVO);
    @POST("/api/food/getOrderDetail")
    Call<FoodOrderDetailVO> getOrderDerail(@Body HashMap map); //주문정보 1건 조회 (회원은 주문코드랑 아이디, 비회원은 주문코드랑 이름)
    @POST("/api/food/getOrderDetailChk")
    Call<HashMap> getOrderDetailChk(@Query("f_name") String username, @Query("f_ocode") String f_ocode); //주문정보 있나 없나만 조회
    @GET("/api/food/modifyFoodStatus")
    Call<HashMap> modifyFoodStatus(@Query("orderCode") String f_ocode, @Query("new_status") String new_status); //주문 상태 변경
    @POST("/api/food/getFoodOrderList")
    Call<FoodOrderListVO> getFoodOrderList(@Query("userid") String userid); //주문 정보 리스트
    @POST("/api/food/foodCouponList")
    Call<FoodCouponVO> foodCouponList(@Query("comp_seq") String comp_seq, @Query("userid") String userid); // 유저 쿠폰 보유 수

    @POST("/api/user/signUp")
    Call<Map<String, String>> memberSignUp(@Body RequestUserVO userVO); //회원가입
    @POST("/api/user/signIn")
    Call<UserVO> memberSignIn(@Body RequestUserVO userVO); //로그인
    @POST("/api/user/modifyUserInfo")
    Call<Map<String, Object>> modifyUserInfo(@Body RequestUserVO userVO); //내정보 불러오기
    @POST("/api/user/modifyUserPwd")
    Call<Map<String, Object>> modifyUserPwd(@Body HashMap<String,String> map); //비밀번호 변경하기
    @POST("/api/user/modifyUserWithdraw")
    Call<Map<String, Object>> modifyUserWithdraw(@Body HashMap<String,String> map); //회원 탈퇴

    //지혜 종료

    //명근 시작
    //매물 수량
    @GET("/api/room/getRoomCnt")
    Call<RoomBVO> getRoomCnt();
    //매물 정보 리스트
    @GET("/api/room/getRoomList")
    Call<RoomBVO> getRoomList(@Query("b_code") String b_code);
    //계약 정보 등록
    @POST("/api/room/insertContract")
    Call<HashMap<String, Object>> insertContract(@Body RoomContractVO roomContractVO);
    //계약 정보 리스트
    @GET("/api/room/getContractList")
    Call<RoomContractDetailVO> getContractList(@Query("userid") String userid);
    //명근 종료

    //카카오페이
    //@POST("/kakao/payTest")
    //Call<HashMap> kakaoPayParkingTicket(@Body FoodOrderVO foodOrderVO);
    @POST
    Call<HashMap> kakaoPaySuccess(@Url String reUrl, @Query("orderCode") String orderCode);
    //카카오페이 끝
}