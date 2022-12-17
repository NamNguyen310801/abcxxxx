package com.example.myappmusic.Adapter;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappmusic.Activity.DownloadActivity;
import com.example.myappmusic.Activity.PlayNhacActivity;
import com.example.myappmusic.Model.Baihat;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatAdapter extends  RecyclerView.Adapter<DanhSachBaiHatAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> arrayList;

    public DanhSachBaiHatAdapter(Context context, ArrayList<Baihat> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view  = inflater.inflate(R.layout.dong_danh_sach_bai_hat,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = arrayList.get(position);
        holder.txtcasi.setText(baihat.getCaSi());
        holder.txttenbaihat.setText(baihat.getTenBaiHat());
        holder.txtindex.setText(String.valueOf(position+1));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayNhacActivity.class);
                intent.putExtra("cakhuc",baihat);
                context.startActivity(intent);
            }
        });
        holder.imgdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DownloadActivity.class);
                Bundle bun = new Bundle();
                bun.putString("tenbaihatdownload",baihat.getTenBaiHat());
                bun.putString("tencasidownload",baihat.getCaSi());
                bun.putString("hinhdownload",baihat.getHinhBaiHat());
                bun.putString("linkdownload",baihat.getLinkBaiHat());
                intent.putExtras(bun);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtindex,txttenbaihat,txtcasi;
        ImageView imgluotthich,imgdownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtindex = itemView.findViewById(R.id.textviewdanhsachindex);
            txttenbaihat = itemView.findViewById(R.id.textviewtenbaihat);
            txtcasi = itemView.findViewById(R.id.textviewtencasi);
            imgluotthich = itemView.findViewById(R.id.imageviewluotthichdanhsachbaihat);
            imgdownload = itemView.findViewById(R.id.imageviewdownload);
            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgluotthich.setImageResource(R.drawable.iconloved);
                    DataService dataService = ApiService.getService();
                    Call<String> call = dataService.UpdateLuotThich("1",arrayList.get(getPosition()).getIdBaiHat());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if(ketqua.equals("Success")){
                                Toast.makeText(context,"Đã thích bài hát " + arrayList.get(getPosition()).getTenBaiHat(),Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context,"Đã xảy ra lỗi",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });
                    imgluotthich.setEnabled(false);
                }
            });



        }
    }
}

