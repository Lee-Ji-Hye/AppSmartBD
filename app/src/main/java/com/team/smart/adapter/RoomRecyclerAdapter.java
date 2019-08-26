package com.team.smart.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.activity.RentalDetailsActivity;
import com.team.smart.vo.RoomVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRecyclerAdapter extends RecyclerView.Adapter<RoomRecyclerAdapter.MyViewHolder> {

    Context context;

    private Map<String,Integer> mRoomImage;

    private List<RoomVO> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView r_img;
        TextView r_name,r_type,r_price,r_reposit,r_ofer_fee,r_floor,r_indi_space,r_able_date,regidate;

        public MyViewHolder(View view) {
            super(view);

            r_img=(ImageView) view.findViewById(R.id.r_img);
            r_name=(TextView) view.findViewById(R.id.r_name);
            r_type=(TextView)view.findViewById(R.id.r_type);
            r_price=(TextView)view.findViewById(R.id.r_price);
            r_reposit=(TextView)view.findViewById(R.id.r_reposit);
            r_ofer_fee=(TextView)view.findViewById(R.id.r_ofer_fee);
            r_floor=(TextView)view.findViewById(R.id.r_floor);
            r_indi_space=(TextView) view.findViewById(R.id.r_indi_space);
            r_able_date=(TextView) view.findViewById(R.id.r_able_date);
            regidate=(TextView) view.findViewById(R.id.regidate);
        }

    }

    public RoomRecyclerAdapter(Context mainActivityContacts, List<RoomVO> offerList) {
        this.OfferList = offerList;
        mRoomImage = new HashMap<>();

        mRoomImage.put("room13", R.drawable.room13);
        mRoomImage.put("room11", R.drawable.img11);

        this.context = mainActivityContacts;
    }

    @Override
    public RoomRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);

        return new RoomRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RoomVO lists = OfferList.get(position);

        holder.r_img.setImageResource(mRoomImage.get(lists.getR_img()));
        holder.r_name.setText(lists.getR_name());
        holder.r_type.setText(lists.getR_type());
        holder.r_price.setText(lists.getR_price());
        holder.r_reposit.setText(lists.getR_reposit());
        holder.r_ofer_fee.setText(lists.getR_ofer_fee());
        holder.r_floor.setText(lists.getR_floor());
        holder.r_indi_space.setText(lists.getR_indi_space());
        holder.r_able_date.setText(lists.getR_able_date());
        holder.regidate.setText(lists.getRegidate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context,  "확인", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, RentalDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public int getItemCount() { return OfferList.size(); }

}
