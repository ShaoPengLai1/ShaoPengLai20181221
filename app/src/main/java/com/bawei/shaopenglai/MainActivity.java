package com.bawei.shaopenglai;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.bawei.shaopenglai.adapter.DaoHangAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url="http://www.zhaoapi.cn/ad/getAd";
    private ViewPager viewPager;
    private Button button;
    private DaoHangAdapter daoHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        viewPager=findViewById(R.id.daohangview_pager);
        button=findViewById(R.id.button);

        final List<String> list=new ArrayList<>();
        list.add("http://www.zhaoapi.cn/images/quarter/ad1.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad2.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad3.png");
        list.add("http://www.zhaoapi.cn/images/quarter/ad4.png");
        daoHangAdapter=new DaoHangAdapter(list,MainActivity.this);
        viewPager.setAdapter(daoHangAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (list.size()-1==i){
                    button.setVisibility(View.VISIBLE);
                }else {
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
