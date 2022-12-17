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

public class BaiHatHotAdapter extends RecyclerView.Adapter<BaiHatHotAdapter.ViewHolder>{
    Context context;
    ArrayList<Baihat> baihatArrayList;

    public BaiHatHotAdapter(Context context, ArrayList<Baihat> baihatArrayList) {
        this.context = context;
        this.baihatArrayList = baihatArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_bai_hat_hot,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Baihat baihat = baihatArrayList.get(position);
        holder.txtcasi.setText(baihat.getCaSi());
        holder.txttenbaihat.setText(baihat.getTenBaiHat());
        Picasso.get().load(baihat.getHinhBaiHat()).into(holder.imghinh);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, PlayNhacActivity.class);
            intent.putExtra("cakhuc", baihat);
            context.startActivity(intent);
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
        return baihatArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenbaihat, txtcasi;
        ImageView imghinh,imgluotthich,imgdownload;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenbaihat= itemView.findViewById(R.id.textbaihathot);
            txtcasi= itemView.findViewById(R.id.textviewcasibaihathot);
            imghinh = itemView.findViewById(R.id.imageviewbaihathot);
            imgdownload = itemView.findViewById(R.id.imageviewdownload);

            imgluotthich= itemView.findViewById(R.id.imageviewluotthich);
            imgluotthich.setOnClickListener(view -> {
                imgluotthich.setImageResource(R.drawable.iconloved);
                DataService  dataService = ApiService.getService();
                Call<String> call = dataService.UpdateLuotThich("1",baihatArrayList.get(getPosition()).getIdBaiHat());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String ketqua = response.body();
                        if(ketqua.equals("Success")){
                            Toast.makeText(context,"Đã thích bài hát " + baihatArrayList.get(getPosition()).getTenBaiHat(),Toast.LENGTH_SHORT).show();
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
            });
        }
    }
}
