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
import com.team.smart.vo.FoodCouponVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FoodCouponListAdapter extends RecyclerView.Adapter<FoodCouponListAdapter.Holder> {

    private Context context;
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private ArrayList<FoodCouponVO.Coupon> foodCouponVO;
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

    public FoodCouponListAdapter(Context context, ArrayList<FoodCouponVO.Coupon> list) {
        this.context = context;
        foodCouponVO = list;
        this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public FoodCouponVO.Coupon getItem(int position)
    {
        //return foodCouponVO.getFoodDetails().get(position);
        return foodCouponVO.get(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View convertView = inflater.inflate(R.layout.food_main_menu, null);
        View convertView = inflater.inflate(R.layout.food_coupon_list, null);
        return new Holder(convertView);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;
        View convertView = holder.view;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_coupon_list, null);
            holder = new Holder(convertView); // ViewHolder을 생성
            holder.tvSerial   = (TextView) convertView.findViewById(R.id.tv_serial);
            holder.tvCouponNum  = (TextView) convertView.findViewById(R.id.tv_coupon_num);
            holder.tvCouponName = (TextView) convertView.findViewById(R.id.tv_coupon_name);
            holder.tvUseDate = (TextView) convertView.findViewById(R.id.tv_use_date);
            holder.tvCouponPrice = (TextView) convertView.findViewById(R.id.tv_coupon_price);
            convertView.setTag(holder); // setTag
        } else {
            holder.tvSerial   = (TextView) convertView.findViewById(R.id.tv_serial);
            holder.tvCouponNum  = (TextView) convertView.findViewById(R.id.tv_coupon_num);
            holder.tvCouponName = (TextView) convertView.findViewById(R.id.tv_coupon_name);
            holder.tvUseDate = (TextView) convertView.findViewById(R.id.tv_use_date);
            holder.tvCouponPrice = (TextView) convertView.findViewById(R.id.tv_coupon_price);
        }

        final FoodCouponVO.Coupon list = foodCouponVO.get(position);

        String[] start =  list.getF_coupon_start().split(" ");
        String[] end =  list.getF_coupon_end().split(" ");

        holder.tvSerial.setText(list.getF_serial());
        holder.tvCouponNum.setText(list.getF_coupon_num());
        holder.tvCouponName.setText(list.getF_coupon_name());
        holder.tvUseDate.setText("사용기한 : "+start[0]+"~"+end[0]);
        holder.tvCouponPrice.setText(list.getF_coupon_price());

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
        //return  foodCouponVO.getFoodDetails().size();
        int cnt = 0;
        if(foodCouponVO != null) {
            cnt = foodCouponVO.size();
        }
        return  cnt;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        public TextView tvSerial, tvCouponNum, tvCouponName,tvUseDate,tvCouponPrice;
        public View view;
        public Holder(View view) {
            super(view);
            this.view = view;
        }
    }

}
