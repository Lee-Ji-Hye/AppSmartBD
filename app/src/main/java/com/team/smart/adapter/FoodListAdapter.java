package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.team.smart.R;
import com.team.smart.activity.DetailActivity;
import com.team.smart.vo.FoodStoreVO;

import java.util.ArrayList;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.Holder> {
    private ArrayList<FoodStoreVO.Store> foodList;
    private Context mCtx;

    public FoodListAdapter(Context context, ArrayList<FoodStoreVO.Store> pList) {
        this.mCtx = context;
        this.foodList = pList;
    }

    @NonNull
    @Override
    public FoodListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_food_list, parent, false);
        return new FoodListAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.Holder holder, int position) {
        final FoodStoreVO.Store food = foodList.get(position);

        holder.txCompName.setText(food.getComp_org());
        holder.txCompComent.setText(food.getShort_desc());
        holder.txReviewCnt.setText(food.getReviewCnt());
        Glide.with(mCtx).load(food.getF_mainimg()).placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).into(holder.f_img);

        Log.d("FOOD DATA ====> ", food.getComp_org());
        //상세 페이지 이동
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("FOOD DATA SUB ====> ", food.getComp_org());
                Intent intent = new Intent(mCtx, DetailActivity.class);
                intent.putExtra("comp_org", food.getComp_org()); /*업체명*/
                intent.putExtra("comp_seq", food.getComp_seq()); /*업체번호*/

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (foodList != null)? foodList.size() : 0;
    }



    public class Holder extends RecyclerView.ViewHolder
    {
        ImageView f_img;
        TextView txCompName, txStar, txReviewCnt, txCompComent;

        public Holder(View view) {
            super(view);

            txCompName  = (TextView) view.findViewById(R.id.tx_comp_name);
            txStar       = (TextView) view.findViewById(R.id.tx_star);
            txReviewCnt = (TextView) view.findViewById(R.id.tx_reviewCnt);
            txCompComent = (TextView)view.findViewById(R.id.tx_comp_coment);
            f_img = (ImageView) view.findViewById(R.id.foodImage);
        }
    }
}
