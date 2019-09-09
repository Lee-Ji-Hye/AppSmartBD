package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.activity.DetailActivity;
import com.team.smart.activity.DetailFoodPopupActivity;
import com.team.smart.activity.FoodCartPopupActivity;
import com.team.smart.vo.FoodDetailVO;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class FoodMainMenuAdapter extends RecyclerView.Adapter<FoodMainMenuAdapter.Holder> {

    private Context context;
    DecimalFormat numberComma = new DecimalFormat("###,###"); //숫자 콤마
    private ArrayList<FoodDetailVO.Menus> foodDetailVO;
    private LayoutInflater inflater;

    //아이템 클릭시 실행 함수
    private ItemClick itemClick;
    public interface ItemClick {
        public void onClick(View view,int position);
    }

    //아이템 클릭시 실행 함수 등록 함수
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FoodMainMenuAdapter(Context context, ArrayList<FoodDetailVO.Menus> list) {
        this.context = context;
        foodDetailVO = list;
        this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    public FoodDetailVO.Menus getItem(int position)
    {
        //return foodDetailVO.getFoodDetails().get(position);
        return foodDetailVO.get(position);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View convertView = inflater.inflate(R.layout.food_main_menu, null);
        View convertView = inflater.inflate(R.layout.food_detail_menu_list, null);
        return new Holder(convertView);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final int pos = position;

        View convertView = holder.view;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.food_main_menu, null);
            holder = new Holder(convertView); // ViewHolder을 생성
            holder.tvBestMenu = (TextView) convertView.findViewById(R.id.tv_food_best_menu);
            holder.liDesc = (LinearLayout) convertView.findViewById(R.id.li_detail_desc);
            holder.tvSubName = (TextView) convertView.findViewById(R.id.tv_food_sub_name);
            holder.tvMainName = (TextView) convertView.findViewById(R.id.tv_food_main_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_food_price);
            holder.f_img = (ImageView) convertView.findViewById(R.id.Food_13_Image_Id);
            convertView.setTag(holder); // setTag
        } else {
            //convertView = holder.view;
           // holder = (Holder) convertView.getTag(); // rootView에서 holder을 꺼내온다
            holder.tvBestMenu = (TextView) convertView.findViewById(R.id.tv_food_best_menu);
            holder.liDesc = (LinearLayout) convertView.findViewById(R.id.li_detail_desc);
            holder.tvSubName = (TextView) convertView.findViewById(R.id.tv_food_sub_name);
            holder.tvMainName = (TextView) convertView.findViewById(R.id.tv_food_main_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_food_price);
            holder.f_img = (ImageView) convertView.findViewById(R.id.Food_13_Image_Id);
        }

        //final FoodDetailVO.FoodDetail detail = foodDetailVO.getFoodDetails().get(position);
        final FoodDetailVO.Menus detail = foodDetailVO.get(position);
        Gson gson = new Gson();
        //String json = gson.toJson(foodDetailVO.getFoodDetails().get(position));
        String json = gson.toJson(foodDetailVO.get(position));
        Log.d("ajskdjaskldjklasdjklas", json);

        holder.tvMainName.setText(detail.getName());
        holder.tvSubName.setText(detail.getSubname());
        holder.tvBestMenu.setText("대표메뉴");
        holder.tvPrice.setText(numberComma.format(Integer.parseInt(detail.getPrice()))+"원");
        Glide.with(context).load(detail.getF_img()).placeholder(R.drawable.no_img)
                .error(R.drawable.no_img).into(holder.f_img);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, pos);
                }
            }
        });


//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // Log.d("FOOD DATA SUB ====> ", detail.getName());
//
//                //Intent intent = new Intent(context, DetailFoodPopupActivity.class);
////                Intent intent = new Intent(context, FoodCartPopupActivity.class);
////                intent.putExtra("fcode", detail.getFcode());       /*메뉴코드*/
////                intent.putExtra("comp_seq", detail.getComp_seq()); /*업체코드*/
////                intent.putExtra("comp_org", detail.getComp_org()); /*업체명*/
////                intent.putExtra("fprice", detail.getPrice());      /*메뉴가격*/
////                intent.putExtra("fname", detail.getName());        /*메뉴이름*/
////
////                context.startActivity(intent);
//
//            }
//        });
        //for(int i = 0; i<2;i++)
        //{
        //    View child = inflater.inflate(R.layout.child_menu, null);
        //    holder.liDesc.addView(child);
        //}
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //return  foodDetailVO.getFoodDetails().size();
        int cnt = 0;
        if(foodDetailVO != null) {
            cnt = foodDetailVO.size();
        }
        return  cnt;
    }


    public static class Holder extends RecyclerView.ViewHolder {
        public LinearLayout liDesc;
        public TextView tvBestMenu, tvMainName, tvSubName, tvPrice;
        public ImageView f_img;
        public View view;
        public Holder(View view) {
            super(view);
            this.view = view;
        }

    }
}
