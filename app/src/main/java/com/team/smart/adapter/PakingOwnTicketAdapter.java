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
import com.team.smart.vo.ParkingTicket;

import java.util.ArrayList;

public class PakingOwnTicketAdapter extends RecyclerView.Adapter<PakingOwnTicketAdapter.Holder> {
    private ArrayList<ParkingTicket> p_List;
    private Context mCtx;

    public PakingOwnTicketAdapter(Context context, ArrayList<ParkingTicket> pList) {
        this.mCtx = context;
        this.p_List = pList;
    }

    @NonNull
    @Override
    public PakingOwnTicketAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ownparkinglist, parent, false);
        return new PakingOwnTicketAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PakingOwnTicketAdapter.Holder holder, int position) {
        final ParkingTicket parkingTicket = p_List.get(position);

        holder.b_name.setText(parkingTicket.getB_name());
        holder.hourly.setText(parkingTicket.getHourly());
        holder.address.setText(parkingTicket.getAddress());
        holder.count.setText(parkingTicket.getCount());

        Log.d("Parking DATA ====> ", parkingTicket.getB_code());
        //상세 페이지 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Parking DATA SUB ====> ", parkingTicket.getB_code());
                Intent intent = new Intent(mCtx, DetailActivity.class);
                intent.putExtra("B_name", parkingTicket.getB_name());

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return p_List.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        TextView b_name,hourly,address,count;
        public Holder(View view) {
            super(view);
            b_name  = (TextView) view.findViewById(R.id.b_name);
            hourly       = (TextView) view.findViewById(R.id.hourly);
            address = (TextView) view.findViewById(R.id.address);
            count = (TextView)view.findViewById(R.id.count);
        }
    }
}
