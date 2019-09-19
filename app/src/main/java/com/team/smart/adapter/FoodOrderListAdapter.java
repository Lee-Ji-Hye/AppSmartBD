package com.team.smart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.vo.FoodOrderListVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FoodOrderListAdapter extends RecyclerView.Adapter<FoodOrderListAdapter.Holder> {

    private Context context;
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private ArrayList<FoodOrderListVO.List> foodOrderListVO;
    private LayoutInflater inflater;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FoodOrderListAdapter(Context context, ArrayList<FoodOrderListVO.List> list) {
        this.context = context;
        foodOrderListVO = list;
        this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public FoodOrderListVO.List getItem(int position)
    {
        //return foodOrderListVO.getFoodDetails().get(position);
        return foodOrderListVO.get(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View convertView = inflater.inflate(R.layout.food_main_menu, null);
        View convertView = inflater.inflate(R.layout.food_order_list, null);
        return new Holder(convertView);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;
        View convertView = holder.view;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_order_list, null);
            holder = new Holder(convertView); // ViewHolder을 생성
            holder.tvOcode   = (TextView) convertView.findViewById(R.id.tv_ocode);
            holder.tvName  = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCompOrg = (TextView) convertView.findViewById(R.id.tv_comp_org);
            holder.tvStatus  = (Button) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder); // setTag
        } else {
            holder.tvOcode   = (TextView) convertView.findViewById(R.id.tv_ocode);
            holder.tvName  = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvCompOrg = (TextView) convertView.findViewById(R.id.tv_comp_org);
            holder.tvStatus  = (Button) convertView.findViewById(R.id.tv_status);
        }

        final FoodOrderListVO.List list = foodOrderListVO.get(position);

        holder.tvOcode.setText(list.getF_ocode());
        holder.tvCompOrg.setText(list.getComp_org());
        holder.tvStatus.setText(list.getF_status());
        holder.tvName.setText(list.getF_name());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, pos);
                }
            }
        });
        
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //return  foodOrderListVO.getFoodDetails().size();
        int cnt = 0;
        if(foodOrderListVO != null) {
            cnt = foodOrderListVO.size();
        }
        return  cnt;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        public TextView tvOcode, tvCompOrg, tvName;
        public Button tvStatus;
        public View view;

        public Holder(View view) {
            super(view);
            this.view = view;
        }
    }

}
