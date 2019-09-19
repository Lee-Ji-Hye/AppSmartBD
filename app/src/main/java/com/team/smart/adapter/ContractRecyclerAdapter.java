package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.activity.RoomContractDetailActivity;
import com.team.smart.vo.RoomContractDetailVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ContractRecyclerAdapter extends RecyclerView.Adapter<ContractRecyclerAdapter.Holder> {
    private ArrayList<RoomContractDetailVO.Contract> contractList;
    Context context;

    public ContractRecyclerAdapter(Context context, ArrayList<RoomContractDetailVO.Contract> contractList) {
        this.context = context;
        this.contractList = contractList;
    }

    @NonNull
    @Override
    public ContractRecyclerAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_contract, parent, false);
        return new ContractRecyclerAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContractRecyclerAdapter.Holder holder, int position) {
        final RoomContractDetailVO.Contract lists = contractList.get(position);

        holder.r_type.setText(lists.getR_type());
        holder.r_price.setText(lists.getR_price()+"/"+lists.getR_deposit());
        holder.b_address.setText(lists.getB_area1()+" "+lists.getB_area2()+" "+lists.getB_address());
        holder.r_floor.setText(lists.getR_floor()+"층");
        if(lists.getR_kind().equals("ST")) {
            holder.r_kind.setText("상가");
        }else{
            holder.r_kind.setText("사무실");
        }

        long batch_date = lists.getRegidate();                              // 통신을 통해 받아온 데이터를 long형 변수에 저장
        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd"); // SimpleDateFormat 클래스를 이용하여 원하는 날짜 형식으로 변경
        System.out.println(sfd.format(batch_date));                         // 출력 확인
        holder.regidate.setText("등록일 : "+sfd.format(batch_date));        // setText

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "계약 정보 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RoomContractDetailActivity.class); //RoomContractDetailActivity 이동할 준비
                intent.putExtra("contractVO", lists);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView r_type, r_price, b_address, r_floor, r_kind, regidate;

        public Holder(View view) {
            super(view);

            r_type = (TextView) view.findViewById(R.id.r_type);
            r_price = (TextView) view.findViewById(R.id.r_price);
            b_address = (TextView) view.findViewById(R.id.b_address);
            r_floor = (TextView) view.findViewById(R.id.r_floor);
            r_kind = (TextView) view.findViewById(R.id.r_kind);
            regidate = (TextView) view.findViewById(R.id.regidate);
        }
    }
}
