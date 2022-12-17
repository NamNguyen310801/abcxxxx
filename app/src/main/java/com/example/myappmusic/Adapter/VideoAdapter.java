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
import com.example.myappmusic.Activity.Video_activity;
import com.example.myappmusic.Model.Album;
import com.example.myappmusic.Model.Video;
import com.example.myappmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends  RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    Context context;
    ArrayList<Video> mangvideo;

    public VideoAdapter(Context context, ArrayList<Video> mangvideo) {
        this.context = context;
        this.mangvideo = mangvideo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = mangvideo.get(position);
        holder.txttenVideo.setText(video.getTenVideo());
        holder.txttencasiVideo.setText(video.getTenTacGia());
        Picasso.get().load(video.getHinhVideo()).into(holder.imgVideo);
    }

    @Override
    public int getItemCount() {
        return mangvideo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgVideo;
        TextView txttenVideo,txttencasiVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideo=itemView.findViewById(R.id.imageviewVideo);
            txttenVideo = itemView.findViewById(R.id.textviewtenVideo);
            txttencasiVideo = itemView.findViewById(R.id.textviewtencasivideo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Video_activity.class);
                    intent.putExtra("idvideo",mangvideo.get(getPosition()).getLinkVideo());
                    context.startActivity(intent);
                }
            });
        }
    }
}
