package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.team.smart.R;
import com.team.smart.adapter.FoodTabFragmentAdapter;

public class FoodListActivity extends AppCompatActivity {

    TabLayout tbFoodMenu;
    ViewPager viewPager;
    FoodTabFragmentAdapter starterFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list2);
        findId(); //아이디 셋팅
        FoodListLoad();

        //Intent intent = new Intent(getApplicationContext(), AKakaoTestActivity.class);
        //this.startActivity(intent);
    }

    public void FoodListLoad() {
        //탭메뉴 붙임
        tbFoodMenu.addTab(tbFoodMenu.newTab().setText("한식"));
        tbFoodMenu.addTab(tbFoodMenu.newTab().setText("중식"));
        tbFoodMenu.addTab(tbFoodMenu.newTab().setText("일식"));
        tbFoodMenu.addTab(tbFoodMenu.newTab().setText("디저트"));

        //탭에 반응하는 프레그먼트 생성하기
        starterFragmentAdapter= new FoodTabFragmentAdapter(getSupportFragmentManager(), tbFoodMenu.getTabCount()); //new 와 동시에 데이터 통신
        viewPager.setOffscreenPageLimit(4); //탭 개수 제한
        viewPager.setAdapter(starterFragmentAdapter); //음식 리스트가 들어갈 프레그먼트 어댑터 세팅
        //탭을 누를때마다 페이지 변경리스너 이벤트 추가
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tbFoodMenu));

        //메뉴턉 변경될떄마다 타는 이벤트
        tbFoodMenu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //onTabUnselected() 호출 후 이곳에 들어옴
                //Toast.makeText(getApplicationContext(), "000", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(tbFoodMenu.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //선택하지 않은 탭을 눌렀을 경우 들어오며 이곳을 호출 한 후 자동으로 onTabSelected()을 감
                //Toast.makeText(getApplicationContext(), "111", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //선택된 탭을 또 눌렀을때 들어옴
                //Toast.makeText(getApplicationContext(), "222", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void findId() {
        tbFoodMenu = findViewById(R.id.tablayout);
        viewPager=findViewById(R.id.viewpager);
    }
}
