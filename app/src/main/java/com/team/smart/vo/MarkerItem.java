package com.team.smart.vo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MarkerItem implements ClusterItem {
    private final LatLng mPosition;
    String r_cnt;
    String b_code;

    public MarkerItem(double lat, double lng, String r_cnt, String b_code) {
        mPosition = new LatLng(lat, lng);
        this.r_cnt = r_cnt;
        this.b_code = b_code;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public String getR_cnt() {
        return r_cnt;
    }

    public void setR_cnt(String r_cnt) {
        this.r_cnt = r_cnt;
    }

    public String getB_code() {
        return b_code;
    }

    public void setB_code(String b_code) {
        this.b_code = b_code;
    }
}
