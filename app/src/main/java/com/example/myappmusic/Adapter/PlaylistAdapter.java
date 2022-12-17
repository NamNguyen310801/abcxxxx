package com.example.myappmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myappmusic.Model.Playlist;
import com.example.myappmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<Playlist> {

    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<Playlist> objects) {
        super(context, resource, objects);
    }

    class ViewHolder{
        TextView txttenPlaylist;
        ImageView imgBackground,imgplaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_playlist,null);
            viewHolder= new ViewHolder();
            viewHolder.txttenPlaylist = convertView.findViewById(R.id.textviewtenplaylist);
            viewHolder.imgplaylist=convertView.findViewById(R.id.imageviewplaylist);
            viewHolder.imgBackground= convertView.findViewById(R.id.imageviewbackgroundplaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Playlist playlist = (Playlist) getItem(position);
        Picasso.get().load(playlist.getHinhPlaylist()).into(viewHolder.imgBackground);
        Picasso.get().load(playlist.getIcon()).into(viewHolder.imgplaylist);
        viewHolder.txttenPlaylist.setText(playlist.getTen());
        return convertView;
    }
}
