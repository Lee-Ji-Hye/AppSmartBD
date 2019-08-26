package com.team.smart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.team.smart.R;
import com.team.smart.adapter.FoodListAdapter;
import com.team.smart.vo.FoodStoreVO;

import java.util.ArrayList;

public class JapaneseDishes extends Dishes {
    //부모 통~신
    RecyclerView rv_foodlist;
    FoodListAdapter foodListAdapter;


    private void init()
    {
        networkResponse = new NetworkResponse() {
            @Override
            public void success(ArrayList<FoodStoreVO.Store> data) {
                //Toast.makeText(getActivity(),"this",Toast.LENGTH_SHORT).show();

                foodListAdapter = new FoodListAdapter(getActivity(), data);
                rv_foodlist.setAdapter(foodListAdapter);
            }

            @Override
            public void failed(String message) {

            }
        };
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_food_list, container, false);

        init();
        rv_foodlist = view.findViewById(R.id.rv_foodlist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_foodlist.setLayoutManager(layoutManager);
        rv_foodlist.setItemAnimator(new DefaultItemAnimator());

        callApiFoodList("일식"); //부모 통신


        return view;

    }
}
