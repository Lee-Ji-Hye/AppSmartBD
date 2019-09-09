package com.team.smart.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.vo.RoomImageVO;

import java.util.List;

public class RoomImageAdapter extends RecyclerView.Adapter<RoomImageAdapter.MyViewHolder>{

    Context context;

    private List<RoomImageVO> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView imageNo;

        public MyViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            imageNo=(TextView) view.findViewById(R.id.imageNo);
        }

    }

    public RoomImageAdapter(Context mainActivityContacts, List<RoomImageVO> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.property_images, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        RoomImageVO lists = OfferList.get(position);
        holder.image.setImageResource(lists.getR_img());
        holder.imageNo.setText(lists.getR_code());
    }

    @Override
    public int getItemCount() {
        return OfferList.size();
    }

}
