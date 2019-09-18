package com.team.smart.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.ContractRecyclerAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.util.SPUtil;
import com.team.smart.vo.RoomContractDetailVO;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomContractListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContractRecyclerAdapter contractAdapter;

    private APIInterface apiContractInterface;

    private String paramUserId;

    //정보
    private ArrayList<RoomContractDetailVO.Contract> contractList;

    protected RoomContractListActivity.NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(RoomContractDetailVO data);
        void  failed(String message);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_contract_list);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_contract);

        paramUserId = SPUtil.getUserId(RoomContractListActivity.this);

        callContractListApi(paramUserId);
    }


    //계약 정보 통신
    protected void callContractListApi(String paramUserId) {

        if(apiContractInterface == null) {
            apiContractInterface = APIClient.getClient().create(APIInterface.class);
        }

        //계약 정보 api통신
        networkResponse = new RoomContractListActivity.NetworkResponse() {
            @Override
            public void success(RoomContractDetailVO data) {
                Toast.makeText(getApplicationContext(),"성공",Toast.LENGTH_SHORT).show();

                //계약 정보 세팅
                contractList = data.getContractList();

                if(contractList == null) {
                    setContentView(R.layout.recycler_view_no_contract);
                }

                contractAdapter = new ContractRecyclerAdapter(RoomContractListActivity.this, contractList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RoomContractListActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(contractAdapter);
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<RoomContractDetailVO> call = apiContractInterface.getContractList(paramUserId);
        call.enqueue(new Callback<RoomContractDetailVO>() {
            @Override
            public void onResponse(Call<RoomContractDetailVO> call, Response<RoomContractDetailVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    RoomContractDetailVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getContractList());
                    Log.d("계약 정보 통신",json3);

                    if(networkResponse!=null) {
                        networkResponse.success(resource);
                    }
                }
            }
            @Override
            public void onFailure(Call<RoomContractDetailVO> call, Throwable t) {
                Log.d("통신 실패", "실패");
                call.cancel();
                if(networkResponse!=null) {
                    networkResponse.failed("통신실패");
                }
            }
        });
    }
}
