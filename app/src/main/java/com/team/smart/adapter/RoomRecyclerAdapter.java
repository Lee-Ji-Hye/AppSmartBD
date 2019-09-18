package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.team.smart.R;
import com.team.smart.activity.RoomDetailActivity;
import com.team.smart.vo.RoomBVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.Holder> {
    private ArrayList<RoomBVO.Room> roomList;
    Context context;

    public RoomRecyclerAdapter(Context context, ArrayList<RoomBVO.Room> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomRecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_room, parent, false);
        return new RoomRecyclerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomRecyclerAdapter.Holder holder, int position) {
        final RoomBVO.Room lists = roomList.get(position);

        Glide.with(context).load(lists.getR_img()).placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).into(holder.r_img);
        if(lists.getR_kind().equals("ST")) {
            holder.r_kind.setText("상가");
        }else{
            holder.r_kind.setText("사무실");
        }
        holder.r_name.setText("매물번호 "+lists.getR_name());
        holder.r_type.setText(lists.getR_type());
        holder.r_price.setText(lists.getR_price()+"/"+lists.getR_deposit());
        holder.r_ofer_fee.setText("관리비 "+lists.getR_ofer_fee()+"만원");
        holder.r_floor.setText(lists.getR_floor()+"층");
        if(lists.getR_indi_space() == null) {
            holder.r_indi_space.setText("없음");
        }else{
            holder.r_indi_space.setText(lists.getR_indi_space());
        }
        if(lists.getR_able_date().equals("0")) {
            holder.r_able_date.setText("즉시 입주 가능");
        }else{
            holder.r_able_date.setText(lists.getR_able_date()+"일 후 입주 가능");
        };

        long batch_date = lists.getRegidate();                              // 통신을 통해 받아온 데이터를 long형 변수에 저장
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd"); // SimpleDateFormat 클래스를 이용하여 원하는 날짜 형식으로 변경
        System.out.println(sfd.format(batch_date));                         // 출력 확인
        holder.regidate.setText("등록일 : "+sfd.format(batch_date));        // setText

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "매물 정보 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RoomDetailActivity.class); //RentalDetailsActivity 이동할 준비
                intent.putExtra("b_area1", lists.getB_area1());
                intent.putExtra("b_area2", lists.getB_area2());
                intent.putExtra("b_address", lists.getB_address());
                intent.putExtra("b_year", lists.getB_year());
                intent.putExtra("b_landarea", lists.getB_landarea());
                intent.putExtra("b_buildarea", lists.getB_buildarea());
                intent.putExtra("b_buildscale", lists.getB_buildscale());

                intent.putExtra("r_kind", lists.getR_kind());
                intent.putExtra("r_code", lists.getR_code());
                intent.putExtra("r_blockCode", lists.getR_blockcode());
                intent.putExtra("r_name", lists.getR_name());
                intent.putExtra("r_type", lists.getR_type());
                intent.putExtra("r_price", lists.getR_price());
                intent.putExtra("r_deposit", lists.getR_deposit());
                intent.putExtra("r_ofer_fee", lists.getR_ofer_fee());
                intent.putExtra("r_floor", lists.getR_floor());
                intent.putExtra("r_indi_space", lists.getR_indi_space());
                intent.putExtra("r_able_date", lists.getR_able_date());
                intent.putExtra("regidate", sfd.format(batch_date));
                intent.putExtra("r_area", lists.getR_area());
                intent.putExtra("r_desc", lists.getR_desc());
                intent.putExtra("userid", lists.getUserid());

                intent.putExtra("b_lat", lists.getB_lat());
                intent.putExtra("b_lon", lists.getB_lon());

                intent.putExtra("name", lists.getName());
                intent.putExtra("email", lists.getEmail());
                intent.putExtra("hp", lists.getHp());

                intent.putExtra("comp_seq", lists.getComp_seq());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView r_img;
        TextView r_type, r_price, r_kind, r_name, r_indi_space, r_floor, r_ofer_fee, r_code, r_able_date, regidate;

        public Holder(View view) {
            super(view);

            r_img = (ImageView) view.findViewById(R.id.r_img);
            r_type = (TextView) view.findViewById(R.id.r_type);
            r_price = (TextView) view.findViewById(R.id.r_price);
            r_kind = (TextView) view.findViewById(R.id.r_kind);
            r_name = (TextView) view.findViewById(R.id.r_name);
            r_indi_space = (TextView) view.findViewById(R.id.r_indi_space);
            r_floor = (TextView) view.findViewById(R.id.r_floor);
            r_ofer_fee = (TextView) view.findViewById(R.id.r_ofer_fee);
            r_able_date = (TextView) view.findViewById(R.id.r_able_date);
            regidate = (TextView) view.findViewById(R.id.regidate);
        }
    }
}
