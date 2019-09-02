package com.team.smart.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.team.smart.R;
import com.team.smart.adapter.FoodMainMenuAdapter;
import com.team.smart.network.APIClient;
import com.team.smart.network.APIInterface;
import com.team.smart.vo.FoodCartVO;
import com.team.smart.vo.FoodDetailVO;
import com.team.smart.vo.FoodStoreVO;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//음식 상세 액티비티
public class DetailActivity extends AppCompatActivity {
    private int REQUESTDETAILFOODCD = 100;
    private int REQUESTFOODCARTCD = 101;

    private LinearLayout isCoupon;
    private TextView tvCouponName, tvCompBranch;
    private Button btnMenu, btnInfo;
    private ImageButton btnback;
    private ImageView mainImg;
    private FrameLayout frTab;
    private RecyclerView recyclerView;
    private FoodMainMenuAdapter foodListAdapter;                  //메뉴 리스트 어댑터
    private FloatingActionButton floatingActionButton;            //장바구니버튼
    private ArrayList<FoodCartVO> foodCartVO = new ArrayList<FoodCartVO>();//장바구니VO

    private String paramCompSeq, paramCompName; //Intent parameter

    private APIInterface apiMenuInterface, apiStoreInterface;
    View viewStoreLongDesc; //업체 긴줄 정보

    //정보
    ArrayList<FoodDetailVO.Menus> foodMenuList;
    ArrayList<FoodStoreVO.Store> foodStore;

    protected NetworkResponse networkResponse;
    interface NetworkResponse {
        void  success(FoodDetailVO data);
        void  failed(String message);
    };


    //메뉴 통신
    protected void callMenuListApi(String paramCompSeq) {

        if(apiMenuInterface == null) {
            apiMenuInterface = APIClient.getClient().create(APIInterface.class);
        }

        //메뉴 api통신
        networkResponse = new NetworkResponse() {
            @Override
            public void success(FoodDetailVO data) {
                //Toast.makeText(getApplicationContext(),"sucess",Toast.LENGTH_SHORT).show();
                //정보세팅
                //업체정보세팅
                foodMenuList = data.getMenuList();
                btnMenu.performClick(); //메뉴버튼 강제 클릭
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<FoodDetailVO> call = apiMenuInterface.foodMenuList(paramCompSeq);
        call.enqueue(new Callback<FoodDetailVO>() {
            @Override
            public void onResponse(Call<FoodDetailVO> call, Response<FoodDetailVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodDetailVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getMenuList());
                    Log.d("디테일 통신~~~~",json3);

                    if(networkResponse!=null) {
                        networkResponse.success(resource);
                    }
                }
            }
            @Override
            public void onFailure(Call<FoodDetailVO> call, Throwable t) {
                Log.d("통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
                if(networkResponse!=null) {
                    networkResponse.failed("통신실패");
                }
            }
        });
    }

    protected NetworkResponse2 networkResponse2;
    interface NetworkResponse2 {
        void  success(FoodStoreVO data);
        void  failed(String message);
    };

    //스토어 통신
    protected void callStoreInfoApi(String paramCompSeq) {
        if(apiStoreInterface == null) {
            apiStoreInterface = APIClient.getClient().create(APIInterface.class);
        }

        //메뉴 디테일리스트 api통신
        networkResponse2 = new NetworkResponse2() {
            @Override
            public void success(FoodStoreVO data) {
                //Toast.makeText(getApplicationContext(),"sucess",Toast.LENGTH_SHORT).show();
                //정보세팅

                //foodStore = ;
                FoodStoreVO.Store store = data.getStores().get(0);
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                viewStoreLongDesc = inflater.inflate(R.layout.food_detail_desc, null);
                TextView tvLongDesc = viewStoreLongDesc.findViewById(R.id.tv_long_desc);
                tvLongDesc.setText(store.getLong_desc());
                //mainImg
                Glide.with(getApplicationContext()).load(store.getF_mainimg()).placeholder(R.drawable.no_img)
                        .error(R.drawable.no_img).into(mainImg);

                //최근 주문수도 넣기 나중에
                //최근 주문수도 넣기 나중에
                //최근 주문수도 넣기 나중에
                //최근 주문수도 넣기 나중에


                //업체정보세팅
                tvCompBranch.setText(store.getComp_branch());
                if(store.getF_coupon_num() == null) {
                    isCoupon.setVisibility(View.GONE);//쿠폰숨김
                } else {
                    isCoupon.setVisibility(View.VISIBLE);//쿠폰보임
                    tvCouponName.setText(store.getF_coupon_name()+" 다운로드");
                }

                btnMenu.performClick(); //메뉴버튼 강제 클릭
            }

            @Override
            public void failed(String message) {

            }
        };

        //통신
        Call<FoodStoreVO> call = apiStoreInterface.foodStore(paramCompSeq);
        call.enqueue(new Callback<FoodStoreVO>() {
            @Override
            public void onResponse(Call<FoodStoreVO> call, Response<FoodStoreVO> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==200) {
                    FoodStoreVO resource = response.body();

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(resource.getStores());
                    Log.d("디테일 스토어 통신~~~~",json3);

                    if(networkResponse2!=null) {
                        networkResponse2.success(resource);
                    }
                }
            }
            @Override
            public void onFailure(Call<FoodStoreVO> call, Throwable t) {
                Log.d("스토어 통신 fail~~~~~~~~~...", "실패..");
                call.cancel();
                if(networkResponse2!=null) {
                    networkResponse2.failed("스토어 통신실패");
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findid();
        configuListner();
        callMenuListApi(paramCompSeq);
        callStoreInfoApi(paramCompSeq);
    }

    private void showBackAlert()
    {
        if(foodCartVO!=null && foodCartVO.size()>0)
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    DetailActivity.this);
            alertDialogBuilder.setTitle("알림");
            // 다이얼로그 생성
            alertDialogBuilder
                    .setMessage("뒤로가기를 누르면 장바구니에 담긴 상품이 삭제됩니다.")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    finish();
                                }
                            })
                    .setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        showBackAlert();
    }

    private void findid() {
        btnback = (ImageButton) findViewById(R.id.back_btn);
        btnInfo = (Button) findViewById(R.id.btn_detail_info);
        btnMenu = (Button) findViewById(R.id.btn_detail_menu);
        frTab = (FrameLayout) findViewById(R.id.fr_tab);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.btnfloating);

        tvCouponName = findViewById(R.id.tv_coupon_name);
        tvCompBranch = findViewById(R.id.tv_comp_branch);
        isCoupon = findViewById(R.id.is_coupon);
        mainImg = findViewById(R.id.f_mainimg);


        Intent intent = getIntent(); /*데이터 수신*/
        paramCompSeq = intent.getExtras().getString("comp_seq"); /*String형*/
        paramCompName = intent.getExtras().getString("comp_org"); /*String형*/

        //세팅
        TextView txCompName = (TextView) findViewById(R.id.tx_comp_name);
        TextView txCompOrg = (TextView) findViewById(R.id.tv_comp_org);
        txCompName.setText(paramCompName);
        txCompOrg.setText(paramCompName);
    }

    private void configuListner() {
        btnMenu.setOnClickListener(view -> {
            //눌렀을때
            btnMenu.setTextColor(Color.argb(255, 0, 0, 0));
            btnInfo.setTextColor(Color.argb(125, 0, 0, 0));
            frTab.removeAllViews();

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view2 = inflater.inflate(R.layout.fragment_food_detail_menu, null);

            recyclerView = view2.findViewById(R.id.rv_mainMenu);
            //recyclerview 사용시 필수  LayoutManager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(DetailActivity.this);
            mLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(mLayoutManager);

            //상단에서 푸드데이터 통신해서 가져오기~~
            //지금은 new FoodDetailVO()로 넣어놓고 new FoodDetailVO()생성자에서 일단 데이터 셋팅하기.

            foodListAdapter = new FoodMainMenuAdapter(DetailActivity.this, foodMenuList);  //<-- 여기에 클릭리스너를 넘긴다~?
            recyclerView.setAdapter(foodListAdapter);
//                view2.findViewById(R.id)
            frTab.addView(view2);//프레임뷰

            //리사이클러 아이템에 클릭 리스너
            foodListAdapter.setItemClick((view1, position) -> {

                Intent intent = new Intent(DetailActivity.this, DetailFoodPopupActivity.class);
                FoodDetailVO.Menus foodDetail = foodListAdapter.getItem(position);
                intent.putExtra("fcode",foodDetail.getFcode());        /*메뉴코드*/
                intent.putExtra("comp_seq", foodDetail.getComp_seq()); /*업체코드*/
                intent.putExtra("comp_org", foodDetail.getComp_org()); /*업체명*/
                intent.putExtra("fprice", foodDetail.getPrice());      /*메뉴가격*/
                intent.putExtra("fname",foodDetail.getName());         /*메뉴이름*/
                intent.putExtra("f_img",foodDetail.getF_img());         /*메뉴이미지*/

                startActivityForResult(intent, REQUESTDETAILFOODCD);
            });

        });

        btnInfo.setOnClickListener(view -> {
            btnMenu.setTextColor(Color.argb(125, 0, 0, 0));
            btnInfo.setTextColor(Color.argb(255, 0, 0, 0));
            frTab.removeAllViews();

//            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//            View view1 = inflater.inflate(R.layout.food_detail_desc, null);
            frTab.addView(viewStoreLongDesc);//프레임뷰
        });

        btnback.setOnClickListener(view->{
            showBackAlert();
        });

        floatingActionButton.setOnClickListener(view -> {
            if(foodCartVO != null && foodCartVO.size() > 0) {
                Intent intent = new Intent(getApplicationContext(), FoodCartPopupActivity.class);
                intent.putExtra("foodList", foodCartVO);
                intent.putExtra("fname", paramCompName);
                intent.putExtra("comp_seq", paramCompSeq);
                startActivityForResult(intent, REQUESTFOODCARTCD);
            } else {
                Toast.makeText(getApplicationContext(),"장바구니 데이터 없음",Toast.LENGTH_SHORT).show();
            }

        });


        /*Intent intent = new Intent(context, FoodCartPopupActivity.class);
//                intent.putExtra("fcode", detail.getFcode());       /*메뉴코드*/
//                intent.putExtra("comp_seq", detail.getComp_seq()); /*업체코드*/
//                intent.putExtra("comp_org", detail.getComp_org()); /*업체명*/
//                intent.putExtra("fprice", detail.getPrice());      /*메뉴가격*/
//                intent.putExtra("fname", detail.getName());        /*메뉴이름*/
//
//                context.startActivity(intent);*/

    }

    //메뉴선택 엑티비티와 카트액티비티 in
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTDETAILFOODCD) {
            if (resultCode == RESULT_OK) {
                if (foodCartVO != null)
                {
                    FoodCartVO cartVO = new FoodCartVO();
                    cartVO.setComp_seq(paramCompSeq);
                    cartVO.setComp_org(paramCompName);
                    cartVO.setF_code(data.getExtras().getString("fcode"));
                    cartVO.setF_name(data.getExtras().getString("fname"));
                    cartVO.setF_price(data.getExtras().getString("fprice"));
                    cartVO.setF_cnt(data.getExtras().getInt("foodcnt",0));
                    if(foodCartVO == null)
                        foodCartVO = new ArrayList<>();
                    boolean stat = false;
                    int i = 0;
                    for(i = 0; i< foodCartVO.size();i++)
                    {
                        if(foodCartVO.get(i).getF_code().equals(cartVO.getF_code()))
                        {
                            stat = true;
                            break;
                        }
                    }
                    if(!stat)
                    {
                        foodCartVO.add(cartVO);
                    }
                    else
                    {
                        foodCartVO.set(i,cartVO);
                    }
                    //담기 알림
//                    Toast.makeText(getApplicationContext(), "장바구니에 메뉴가 추가되었습니다.", Toast.LENGTH_SHORT)
//                         .setGravity().show();
                    Toast toast = Toast.makeText(getApplicationContext(),"장바구니에 메뉴가 추가되었습니다.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
//                    fcode = intent.getExtras().getString("fcode");       /*String형*/
//                    comp_seq = intent.getExtras().getString("comp_seq"); /*String형*/
//                    comp_org = intent.getExtras().getString("comp_org"); /*String형*/
//                    fprice = intent.getExtras().getString("fprice");     /*String형*/
//                    fname = intent.getExtras().getString("fname");       /*String형*/
                }

            }

        } else if (requestCode == REQUESTFOODCARTCD) {
            if (resultCode == 200) {
                foodCartVO = (ArrayList<FoodCartVO>) data.getSerializableExtra("foodList");

                Gson gson = new Gson();
                String json = gson.toJson(foodCartVO);
                Log.d("장바구니 :::: ", json);
            }
        }
    }
}