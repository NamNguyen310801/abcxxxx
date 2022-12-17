package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myappmusic.Adapter.DanhSachTatCaAlbumAdapter;
import com.example.myappmusic.Model.Album;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtatcaalbumActivity extends AppCompatActivity {
    RecyclerView recyclerViewallalbum;
    Toolbar toolbaralbum;
    ArrayList<Album> albumArrayList;
    DanhSachTatCaAlbumAdapter danhSachTatCaAlbumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcaalbum);
        anhXa();
        getData();
    }
    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<Album>> call =  dataService.GetDanhSachAlbum();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                albumArrayList = (ArrayList<Album>) response.body();
                danhSachTatCaAlbumAdapter = new DanhSachTatCaAlbumAdapter(DanhsachtatcaalbumActivity.this,albumArrayList);
                recyclerViewallalbum.setLayoutManager(new GridLayoutManager(DanhsachtatcaalbumActivity.this,2));
                recyclerViewallalbum.setAdapter(danhSachTatCaAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        recyclerViewallalbum = findViewById(R.id.recyclerviewAllalbum);
        toolbaralbum = findViewById(R.id.toolbaralbum);
        setSupportActionBar(toolbaralbum);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả Album");
        toolbaralbum.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}