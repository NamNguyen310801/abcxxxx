package com.example.myappmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappmusic.Activity.DanhSachVideoActivity;
import com.example.myappmusic.Adapter.VideoAdapter;
import com.example.myappmusic.Model.Video;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Video extends Fragment {
    View view;
    RecyclerView recyclerViewvideo;
    TextView textviewxemthemVideo;
    VideoAdapter videoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_video,container,false);
        recyclerViewvideo = view.findViewById(R.id.recyclerviewVideo);
        textviewxemthemVideo = view.findViewById(R.id.textviewxemthemVideo);
        textviewxemthemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DanhSachVideoActivity.class);
                startActivity(intent);
            }
        });
        getData();
        return  view;
    }

    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<Video>> callback = dataService.GetVideoHot();
        callback.enqueue(new Callback<List<Video>>() {
            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                ArrayList<Video> videoArrayList = (ArrayList<Video>) response.body();
                videoAdapter = new VideoAdapter(getActivity(), videoArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerViewvideo.setLayoutManager(linearLayoutManager);
                recyclerViewvideo.setAdapter(videoAdapter);
            }
            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {

            }
        });
    }

}
