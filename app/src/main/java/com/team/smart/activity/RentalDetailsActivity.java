package com.team.smart.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.team.smart.R;
import com.team.smart.adapter.MyCustomPagerAdapter;

public class RentalDetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    int images[] = {R.drawable.property, R.drawable.property, R.drawable.property, R.drawable.property};
    MyCustomPagerAdapter myCustomPagerAdapter;

    private TextView imageNo, rentalContract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_details);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        imageNo = (TextView)findViewById(R.id.imageNo);
        rentalContract = findViewById(R.id.RentalContract);

        myCustomPagerAdapter = new MyCustomPagerAdapter(RentalDetailsActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                imageNo.setText("" + (position +1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        rentalContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "임대 버튼 눌림", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), RentalContractActivity.class); //ParkingMainActivity 이동할 준비
                startActivity(intent);
            }
        });

    }

}
