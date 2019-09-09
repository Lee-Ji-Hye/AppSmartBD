package com.team.smart.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {

    public static String SHARED_USERID = "SHARED_USERID"; //키값 매칭
    public static String SHARED_USERNAME = "SHARED_USERNAME"; //키값 매칭
    public static String SHARED_USERHP = "SHARED_USERHP"; //키값 매칭
    public static String SHARED_USEREMAIL = "SHARED_USEREMAIL"; //키값 매칭

    private static final int MODE_PRIVATE = 0;
    private static final String userinfo = "USERINFO";


    // 아이디 불러오기
    public static String getUserId(Context context){
        SharedPreferences pref = context.getSharedPreferences(userinfo,MODE_PRIVATE);
        return pref.getString(SHARED_USERID, "");
    }

    // 아이디 저장
    public static void setUserId(Context context,String userId){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHARED_USERID, userId);
        editor.commit();
    }

    //이름 불러오기
    public static String getUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences(userinfo,MODE_PRIVATE);
        return pref.getString(SHARED_USERNAME, ""); //값이 없을경우 기본 ""값 세팅
    }

    // 이름 세팅
    public static void setUserName(Context context,String userId){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHARED_USERNAME, userId);
        editor.commit();
    }

    //핸드폰번호 불러오기
    public static String getUserHP(Context context){
        SharedPreferences pref = context.getSharedPreferences(userinfo,MODE_PRIVATE);
        return pref.getString(SHARED_USERHP, ""); //값이 없을경우 기본 ""값 세팅
    }

    // 핸드폰번호 세팅
    public static void setUserHP(Context context,String hp){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHARED_USERHP, hp);
        editor.commit();
    }

    //이메일 불러오기
    public static String getUserEmail(Context context){
        SharedPreferences pref = context.getSharedPreferences(userinfo,MODE_PRIVATE);
        return pref.getString(SHARED_USEREMAIL, ""); //값이 없을경우 기본 ""값 세팅
    }

    // 이메일 세팅
    public static void setUserEmail(Context context,String email){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SHARED_USEREMAIL, email);
        editor.commit();
    }

    // 값(Key Data) 삭제하기
    public static void removePreferences(Context context,String key){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        editor.commit();
    }

    // 값(ALL Data) 삭제하기
    public static void removeAllPreferences(Context context){
        SharedPreferences pref = context.getSharedPreferences(userinfo, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }


}
