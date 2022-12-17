package com.example.myappmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaiHatAdapter extends  RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder>{
Context context;
public ArrayList<Baihat> mangbaihat;

    public SearchBaiHatAdapter(Context context, ArrayList<Baihat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dong_search_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Baihat baihat = mangbaihat.get(position);
    holder.txtTenbaihat.setText(baihat.getTenBaiHat());
    holder.txtCasi.setText(baihat.getCaSi());
    Picasso.get().load(baihat.getHinhBaiHat()).into(holder.imgbaihat);
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
        return mangbaihat.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenbaihat, txtCasi;
        ImageView imgbaihat, imgluotthich,imgdownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgdownload = itemView.findViewById(R.id.imageviewdownload2);
            txtTenbaihat = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtBaiHat);
            txtCasi = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtCaSi);
            imgbaihat = itemView.findViewById(R.id.itemBaiHatNgauNhien_imgBaiHat);
            imgluotthich = itemView.findViewById(R.id.itemBaiHatNgauNhien_txtCaSi_icLove);
            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, PlayNhacActivity.class);
                intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                context.startActivity(intent);
            });


            imgluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imgluotthich.setImageResource(R.drawable.iconloved);
                    DataService dataService = ApiService.getService();
                    Call<String> call = dataService.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdBaiHat());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String ketqua = response.body();
                            if(ketqua.equals("Success")){
                                Toast.makeText(context,"Da thich", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context,"Loi", Toast.LENGTH_SHORT).show();
                        }
                    });
                    imgluotthich.setEnabled(false);
                }
            });
        }
    }
}
