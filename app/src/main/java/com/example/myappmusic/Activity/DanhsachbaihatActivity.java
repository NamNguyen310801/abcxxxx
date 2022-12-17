package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.example.myappmusic.Adapter.DanhSachBaiHatAdapter;
import com.example.myappmusic.Model.Album;
import com.example.myappmusic.Model.Baihat;
import com.example.myappmusic.Model.Playlist;
import com.example.myappmusic.Model.Quangcao;
import com.example.myappmusic.Model.TheLoai;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachbaihatActivity extends AppCompatActivity {
    Quangcao quangcao;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ImageView imgdanhsachcakhuc;
    public  static ArrayList<Baihat> baihatArrayList;
    DanhSachBaiHatAdapter danhSachBaiHatAdapter;
    Playlist playlist;
    TheLoai theLoai;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        DataIntent();
        anhXa();
        khoiTao();
        if(quangcao != null && !quangcao.getTenBaiHat().equals("")){
            setValueInview(quangcao.getTenBaiHat(),quangcao.getHinhBaiHat());
            getDataQuangCao(quangcao.getIdQuangCao());
        }else if(playlist != null && !playlist.getTen().equals("")){
            setValueInview(playlist.getTen(),playlist.getHinhPlaylist());
            getDataPlaylist(playlist.getIdPlaylist());
        }
        else if(theLoai != null && !theLoai.getTenTheLoai().equals("")){
            setValueInview(theLoai.getTenTheLoai(),theLoai.getHinhTheLoai());
            getDataTheLoai(theLoai.getIdTheLoai());
        }
        else if(album != null && !album.getTenAlbum().equals("")){
            setValueInview(album.getTenAlbum(),album.getHinhAlbum());
            getDataAlbum(album.getIdAlbum());
        }
    }

    private void getDataAlbum(String idalbum) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> call = dataService.GetDanhsachbaihattheoalbum(idalbum);
        call.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,baihatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhsachbaihatActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
                Log.d("tag", "Load data danhsachbaihat fail");
            }
        });
    }

    private void getDataTheLoai(String idtheloai) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> call = dataService.GetDanhsachbaihattheotheloai(idtheloai);
        call.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,baihatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhsachbaihatActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
                Log.d("tag", "Load data danhsachbaihat fail");
            }
        });
    }

    private void getDataPlaylist(String idplaylist) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> listCall = dataService.GetDanhsachbaihattheoplaylist(idplaylist);
        listCall.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,baihatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhsachbaihatActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
                Log.d("tag", "Load data danhsachbaihat fail");
            }
        });
    }

    private void getDataQuangCao(String idquangcao) {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> listCall = dataService.GetDanhsachbaihattheoquangcao(idquangcao);
        listCall.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baihatArrayList = (ArrayList<Baihat>) response.body();
                danhSachBaiHatAdapter = new DanhSachBaiHatAdapter(DanhsachbaihatActivity.this,baihatArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DanhsachbaihatActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(danhSachBaiHatAdapter);
                eventClick();
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
                Log.d("tag", "Load data danhsachbaihat fail");
            }
        });
    }

    private void setValueInview(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            // fix bug android.os.NetworkOnMainThreadException
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.get().load(hinh).into(imgdanhsachcakhuc);
    }

    private void anhXa() {
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        toolbar = findViewById(R.id.toolbardanhsach);
        collapsingToolbarLayout = findViewById(R.id.collaptoolbar);
        recyclerView = findViewById(R.id.recyclerviewdsbaihat);
        floatingActionButton = findViewById(R.id.floatingactiocbutton);
        imgdanhsachcakhuc = findViewById(R.id.imageviewdanhsachcakhuc);
        floatingActionButton.setEnabled(false);
    }
    private void khoiTao() {
        setSupportActionBar(toolbar);
        //cai dat hinh mui ten quay tro ve trang tru
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if(intent != null){
            if(intent.hasExtra("banner")){
                quangcao =(Quangcao) intent.getSerializableExtra("banner");
            }
            if(intent.hasExtra("itemplaylist")){
                playlist =(Playlist) intent.getSerializableExtra("itemplaylist");
            }
            if(intent.hasExtra("idtheloai")){
                theLoai =(TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if(intent.hasExtra("idalbum")){
                album =(Album) intent.getSerializableExtra("idalbum");
            }
        }
    }
        private void eventClick(){

            floatingActionButton.setEnabled(true);
            floatingActionButton.setOnClickListener(view -> {
                Intent i = new Intent(DanhsachbaihatActivity.this,PlayNhacActivity.class);
                i.putExtra("caccakhuc",baihatArrayList);
                startActivity(i);
            });
        }


}