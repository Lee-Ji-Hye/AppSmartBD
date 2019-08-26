package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

public class BaseRespon {
    @SerializedName("responseCode")
    private int responseCode;
    @SerializedName("responseMsg")
    private String responseMsg;
    public BaseRespon()
    {
        responseCode = 0;
        responseMsg = "";
    }
}
