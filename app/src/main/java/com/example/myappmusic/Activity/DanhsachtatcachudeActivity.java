package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.myappmusic.Adapter.DanhSachTatCaChuDeAdapter;
import com.example.myappmusic.Model.ChuDe;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtatcachudeActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcachude;
    Toolbar toolbartatcachude;
    ArrayList<ChuDe> chuDeArrayList;
    DanhSachTatCaChuDeAdapter danhSachTatCaChuDeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcachude);
        anhXa();
        getData();
    }
    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<ChuDe>> call =  dataService.GetDanhsachcacchude();
        call.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                chuDeArrayList = (ArrayList<ChuDe>) response.body();
                danhSachTatCaChuDeAdapter = new DanhSachTatCaChuDeAdapter(DanhsachtatcachudeActivity.this,chuDeArrayList);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(DanhsachtatcachudeActivity.this,1));
                recyclerViewtatcachude.setAdapter(danhSachTatCaChuDeAdapter);

            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        recyclerViewtatcachude = findViewById(R.id.recyclerviewAllchude);
        toolbartatcachude = findViewById(R.id.toolbarchude);
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả chủ để");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}