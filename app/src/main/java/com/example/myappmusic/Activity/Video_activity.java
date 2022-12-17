package com.example.myappmusic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myappmusic.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Video_activity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    Button buttonPlay, buttonBack;
    TextView txtVideo;
    String idVideo="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        getData();
        anhXa();
    }
    private void getData() {
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        idVideo = bun.getString("idvideo");
    }
    private void anhXa() {
        youTubePlayerView = findViewById(R.id.youtubeplayerView);
        buttonPlay = findViewById(R.id.buttonyoutubePlay);
        buttonBack = findViewById(R.id.buttonyoutubeBack);
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = idVideo;
                youTubePlayer.cueVideo(videoId, 0);
                buttonPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        youTubePlayer.loadVideo(videoId,0);
                    }
                });
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}