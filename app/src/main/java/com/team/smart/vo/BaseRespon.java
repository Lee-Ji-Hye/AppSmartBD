package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

public class BaseRespon {
    @SerializedName("responseCode")
    private int responseCode;
    @SerializedName("responseMsg")
    private String responseMsg;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public BaseRespon()
    {
        responseCode = 0;
        responseMsg = "";


    }
}
