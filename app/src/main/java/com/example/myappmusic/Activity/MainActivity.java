package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.example.myappmusic.Adapter.MainViewPagerAdapter;
import com.example.myappmusic.Fragment.Fragment_Tim_Kiem;
import com.example.myappmusic.Fragment.Fragment_Trang_Chu;
import com.example.myappmusic.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        init();
    }
    public void mapping() {
        tabLayout =  findViewById(R.id.myTabLayout);
        viewPager =  findViewById(R.id.myViewPager);
    }

    public void init() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_Trang_Chu(), "Trang chủ");
        mainViewPagerAdapter.addFragment(new Fragment_Tim_Kiem(), "Tìm kiếm");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);//set main cho tablayout
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.icontimkiem);
        tabLayout.setBackgroundColor(Color.rgb(177, 177, 177));
    }
}