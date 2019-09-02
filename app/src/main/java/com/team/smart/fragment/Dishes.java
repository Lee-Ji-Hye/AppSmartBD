package com.team.smart.fragment;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodStoreVO;
import com.team.smart.vo.Foods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dishes extends Fragment {

    protected NetworkResponse networkResponse;
    interface NetworkResponse {
       void  success(ArrayList<FoodStoreVO.Store> data);
        void  failed(String message);
    };

    private APIInterface apiInterface;
    private List<Foods.Food> datumList;

    public Dishes() {
        if(apiInterface == null) {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        }
    }

    //통신
    protected void callApiFoodList(String f_category) {
        /**
         GET List Resources
         **/
        //HashMap<String,String> param = new HashMap<>();
        //param.put("f_category",f_category);

        Call<FoodStoreVO> call = apiInterface.DishesList(f_category, null);
        call.enqueue(new Callback<FoodStoreVO>() {
            @Override
            public void onResponse(Call<FoodStoreVO> call, Response<FoodStoreVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodStoreVO resource = response.body();
                    if(networkResponse!=null) {
                        networkResponse.success(resource.getStores());
                    }
                }
            }
            @Override
            public void onFailure(Call<FoodStoreVO> call, Throwable t) {
                Log.d("통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
                if(networkResponse!=null) {
                    networkResponse.failed("통신실패");
                }
            }
        });

//        Gson gson3 = new Gson();
//        String json3 = gson3.toJson(datumList);
//        Log.d("여기에도 오는지~~~>", json3);
    }
}
