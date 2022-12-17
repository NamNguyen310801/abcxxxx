package com.example.myappmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappmusic.Model.VideoYouTube;
import com.example.myappmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoYouTubeAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<VideoYouTube> videoYouTubeList;

    public VideoYouTubeAdapter(Context context, int layout, List<VideoYouTube> videoYouTubeList) {
        this.context = context;
        this.layout = layout;
        this.videoYouTubeList = videoYouTubeList;
    }

    @Override
    public int getCount() {
        return videoYouTubeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private  class  ViewHolder{
        ImageView imgThumbnail;
        TextView txtTitle;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtTitle = view.findViewById(R.id.textviewTitle);
            viewHolder.imgThumbnail = view.findViewById(R.id.imageviewthumbnail);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        VideoYouTube videoYouTube = videoYouTubeList.get(i);
        viewHolder.txtTitle.setText(videoYouTube.getTitle());
        Picasso.get().load(videoYouTube.getThumbnail()).into(viewHolder.imgThumbnail);


        return view;
    }
}
