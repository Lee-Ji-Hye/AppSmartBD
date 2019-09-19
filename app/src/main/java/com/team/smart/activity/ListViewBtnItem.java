package com.team.smart.activity;
import android.graphics.drawable.Drawable;

public class ListViewBtnItem {
    private String textStr1 ;
    private String textStr2 ;

    public ListViewBtnItem() {
    }

    public ListViewBtnItem(String textStr1, String textStr2) {
        this.textStr1 = textStr1;
        this.textStr2 = textStr2;
    }

    public String getTextStr1() {
        return textStr1;
    }

    public void setTextStr1(String textStr1) {
        this.textStr1 = textStr1;
    }

    public String getTextStr2() {
        return textStr2;
    }

    public void setTextStr2(String textStr2) {
        this.textStr2 = textStr2;
    }
}