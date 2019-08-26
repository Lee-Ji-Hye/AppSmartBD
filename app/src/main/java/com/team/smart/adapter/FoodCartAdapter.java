package com.team.smart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.vo.FoodCartVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FoodCartAdapter extends RecyclerView.Adapter<FoodCartAdapter.Holder> {
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private ArrayList<FoodCartVO> foodList;
    private Context mCtx;

    private FoodCartAdapter.ItemClick itemClick;

    public interface ItemClick {
        public void onPlus(View view, int position);
        public void onMinus(View view, int position);
        public void onDel(View view, int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(FoodCartAdapter.ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FoodCartAdapter(Context context, ArrayList<FoodCartVO> pList) {
        this.mCtx = context;
        this.foodList = pList;
    }

    @NonNull
    @Override
    public FoodCartAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_cart_list, parent, false);
        return new FoodCartAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodCartAdapter.Holder holder, int position) {
        final FoodCartVO food = foodList.get(position);

        int fprice = Integer.parseInt(food.getF_price());

        holder.tvFname.setText(food.getF_name());
        holder.tvdfPrice.setText((numberComma.format(fprice * food.getF_cnt())) + "원");
        holder.tvCnt.setText(food.getF_cnt() + "개");
        holder.tvDefaultDescrption.setText("기본 금액:" + numberComma.format(fprice)+ "원");
        holder.btnMinus.setTag(position);
        holder.btnplus.setTag(position);
        holder.btnplus.setOnClickListener(view -> {
            if (itemClick != null) {
                itemClick.onPlus(view, position);
            }
        });
        holder.btnMinus.setOnClickListener(view -> {
            if (itemClick != null) {
                itemClick.onMinus(view, position);
            }
        });
        holder.btnDel.setOnClickListener(view -> {
            if (itemClick != null) {
                itemClick.onDel(view, position);
            }

        });
//        //상세 페이지 이동
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("FOOD DATA SUB ====> ", food.getComp_org());
//                Intent intent = new Intent(mCtx, DetailActivity.class);
//                intent.putExtra("comp_seq", food.getComp_org()); /*업체명*/
//
//                mCtx.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView tvCnt, tvdfPrice, tvFname, tvDefaultDescrption;
        Button   btnDel;
        EditText btnplus, btnMinus;

        public Holder(View view) {
            super(view);
            btnDel = (Button) view.findViewById(R.id.tv_food_cart_delbtn);
            tvCnt = (TextView) view.findViewById(R.id.tv_food_cart_cnt);
            tvdfPrice = (TextView) view.findViewById(R.id.tv_food_cart_defprice);
            tvFname = (TextView) view.findViewById(R.id.tv_food_cart_fname);
            tvDefaultDescrption = (TextView) view.findViewById(R.id.tv_food_cart_default);
            btnplus = (EditText) view.findViewById(R.id.bt_plusBtn);
            btnMinus = (EditText) view.findViewById(R.id.bt_minusBtn);
            //imgUrl = (ImageView) view.findViewById(R.id.img_url);
        }
    }
}