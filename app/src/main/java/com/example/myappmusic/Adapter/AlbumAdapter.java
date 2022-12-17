package com.example.myappmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappmusic.Activity.DanhsachbaihatActivity;
import com.example.myappmusic.Model.Album;
import com.example.myappmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;
    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Album album = albumArrayList.get(position);
        holder.txtcasialbum.setText(album.getTenCaSiAlbum());
        holder.txttenalbum.setText(album.getTenAlbum());
        Picasso.get().load(album.getHinhAlbum()).into(holder.imgalbum);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgalbum;
        TextView txttenalbum,txtcasialbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgalbum=itemView.findViewById(R.id.imageviewalbum);
            txtcasialbum = itemView.findViewById(R.id.textviewtencasialbum);
            txttenalbum = itemView.findViewById(R.id.textviewtenalbum);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DanhsachbaihatActivity.class);
                    intent.putExtra("idalbum",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}

