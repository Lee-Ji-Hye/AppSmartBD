package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.activity.DetailActivity;
import com.team.smart.vo.ParkingTicketVO;

import java.util.ArrayList;

public class ParkingTicketAdapter extends RecyclerView.Adapter<ParkingTicketAdapter.ViewHolder> {

    private ArrayList<ParkingTicketVO.ParkingTicket> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourText, hourlyTV,priceTV;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            hourText = itemView.findViewById(R.id.hourText) ;
            hourlyTV = itemView.findViewById(R.id.hourlyTV) ;
            priceTV = itemView.findViewById(R.id.priceTV) ;
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ParkingTicketAdapter(ArrayList<ParkingTicketVO.ParkingTicket> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ParkingTicketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.parking_ticketinfo_list, parent, false) ;
        ParkingTicketAdapter.ViewHolder vh = new ParkingTicketAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ParkingTicketAdapter.ViewHolder holder, int position) {
        ParkingTicketVO.ParkingTicket ticket = mData.get(position) ;
        String h_text ="";
        String h_type = ticket.getP_type();
        String hour = ticket.getHourly();

        if (h_type.equalsIgnoreCase("h")){
            h_text="시간권 [";
            hour=hour+"시간]";
        }else if (h_type.equalsIgnoreCase("d")){
            h_text="일일권";
            hour ="";
        }else{
            h_text="시간권 [";
            hour=hour+"분]";
        }


        holder.hourText.setText(h_text);
        holder.hourlyTV.setText(hour);
        holder.priceTV.setText(ticket.getPrice());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}