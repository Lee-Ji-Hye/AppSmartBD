package com.team.smart.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team.smart.R;
import com.team.smart.vo.ParkingTicket;

import java.util.List;

public class ParkingTicketAdapter extends BaseAdapter {

    private List<ParkingTicket> mData;

    public ParkingTicketAdapter(List<ParkingTicket> mData) {
        this.mData = mData;
    }
    // 아이템의 갯수
    @Override
    public int getCount() {
        return mData.size();
    }
    // position 번째의 아이템

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    // position 번째의 아이디
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder; // 맨 아래 Viewholder 클래스를 작성 후 선언한다.

        if(convertView == null){    // 처음 1번만 convertView를 객체화 (싱글톤 느낌)
            holder = new ViewHolder();  // 성능개선 클래스 생성
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_ticketinfo_list, parent,false);
            // attachToRoot: false = 이 레이아웃이 루트 레이아웃인지를 지정한다.. 리스트뷰의 각 아이템이므로 false를 지정

            // 날씨, 도시명, 기온 View
            // 시간, 금액

            TextView hourlyTV = convertView.findViewById(R.id.hourlyTV);
            TextView priceTV = convertView.findViewById(R.id.priceTV);

            holder.hourlyTV = hourlyTV;
            holder.priceTV = priceTV;

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag(); // holder
        }

        ParkingTicket parkingTicket = mData.get(position);

        //holder.hourlyTV.setText(parkingTicket.getHourly());
       // holder.priceTV.setText(parkingTicket.getPrice());
        return convertView;
    }

    // 성능개선 클래스 ViewHolder 패턴은 자주 사용하는 뷰를 한번 로드하면 재사용하고, 표시할 내용만 교체하기위한 패턴이다.
    static class ViewHolder {
        TextView hourlyTV;
        TextView priceTV;
    }
}
