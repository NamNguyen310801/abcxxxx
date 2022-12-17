package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.myappmusic.Adapter.ViewPagerPlaylistnhacAdapter;
import com.example.myappmusic.Fragment.Fragment_Dia_Nhac;
import com.example.myappmusic.Fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;
import com.example.myappmusic.Model.Baihat;
import com.example.myappmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity {
public static ArrayList<Baihat> baiHatArrayList  = new ArrayList<>();
public static ViewPagerPlaylistnhacAdapter viewPagerAdapterPlayNhac;
private Fragment_Dia_Nhac fragmentDiaNhac;
private Fragment_Play_Danh_Sach_Cac_Bai_Hat fragmentPlayDanhSachCacBaiHat;
private Baihat baiHat;
MediaPlayer mediaPlayer;
Toolbar toolbar;
SeekBar seekbar;
TextView txtTimeStart, txtTimeEnd;
ImageButton btnSuffle, btnPrev, btnPlay, btnNext, btnRepeat;
ViewPager viewPager;
boolean repeat = false;
boolean shuffle = false;
boolean play = false;
int positon = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
//tranh tinh trang phat sinh van de mang, sd code nay de kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DataIntent();
        anhXa();
        init();
        eventClick();
    }

    private void eventClick() {
Handler handler = new Handler();
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        if(viewPagerAdapterPlayNhac.getItem(1) != null){
            if(baiHatArrayList.size() >0){
                fragmentDiaNhac.setImgDiaNhac(baiHatArrayList.get(0).getHinhBaiHat());
                handler.removeCallbacks(this);
            }
            else{
                handler.postDelayed(this,300);
            }
        }
    }
},500);
    btnPlay.setOnClickListener(view -> {
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.iconplay);
        }else{
            mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.iconpause);
        }
    });

    //nut repeat
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat == false){
                    if(shuffle == true){
                        shuffle = false;
                        btnRepeat.setImageResource(R.drawable.iconsyned);
                        btnSuffle.setImageResource(R.drawable.iconsuffle);
                    }
                    btnRepeat.setImageResource(R.drawable.iconsyned);
                    repeat= true;
                }
                else{
                    btnRepeat.setImageResource(R.drawable.iconrepeat);
                    repeat= false;
                }
            }
        });
        //nut random
        btnSuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shuffle == false){
                    if(repeat == true){
                        repeat = false;
                        btnSuffle.setImageResource(R.drawable.iconshuffled);
                        btnRepeat.setImageResource(R.drawable.iconrepeat);

                    }
                    btnSuffle.setImageResource(R.drawable.iconshuffled);
                    shuffle= true;
                }
                else{
                    btnSuffle.setImageResource(R.drawable.iconsuffle);
                    shuffle= false;
                }
            }
        });

        //seekbar
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekbar.getProgress());
            }
        });

        //nur next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baiHatArrayList.size() >0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(positon < (baiHatArrayList.size())){
                        btnPlay.setImageResource(R.drawable.iconpause);
                        positon++;
                        if(repeat == true){
                            if(positon==0){
                                positon = baiHatArrayList.size();
                            }
                            positon-=1;
                        }
                        if(shuffle==true){
                            Random random = new Random();
                            int index = random.nextInt(baiHatArrayList.size());
                            if(index == positon){
                                positon = index -1;
                            }
                            positon = index;
                        }
                        if (positon> (baiHatArrayList.size()-1)){
                            positon = 0;
                        }
                        new PlayMp3().execute(baiHatArrayList.get(positon).getLinkBaiHat());
                        fragmentDiaNhac.setImgDiaNhac(baiHatArrayList.get(positon).getHinhBaiHat());
                        getSupportActionBar().setTitle(baiHatArrayList.get(positon).getTenBaiHat());
                        UpdateTime();
                    }
                }
                btnPrev.setClickable(false);
                btnNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPrev.setClickable(true);
                        btnNext.setClickable(true);
                    }
                },5000);
            }
        });
        //nut pre
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baiHatArrayList.size() >0){
                    if(mediaPlayer.isPlaying() || mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if(positon < (baiHatArrayList.size())){
                        btnPlay.setImageResource(R.drawable.iconpause);
                        positon--;
                        if(positon <0 ){
                            positon = baiHatArrayList.size() -1;
                        }
                        if(repeat == true){
                            positon +=1;
                        }
                        if(shuffle==true){
                            Random random = new Random();
                            int index = random.nextInt(baiHatArrayList.size());
                            if(index == positon){
                                positon = index -1;
                            }
                            positon = index;
                        }

                        new PlayMp3().execute(baiHatArrayList.get(positon).getLinkBaiHat());
                        fragmentDiaNhac.setImgDiaNhac(baiHatArrayList.get(positon).getHinhBaiHat());
                        getSupportActionBar().setTitle(baiHatArrayList.get(positon).getTenBaiHat());
                        UpdateTime();
                    }
                }
                btnPrev.setClickable(false);
                btnNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnPrev.setClickable(true);
                        btnNext.setClickable(true);
                    }
                },5000);
            }
        });
    }



    private void init() {
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            mediaPlayer.stop();
            baiHatArrayList.clear();
        }
    });
        toolbar.setTitleTextColor(Color.WHITE);
        fragmentPlayDanhSachCacBaiHat = new Fragment_Play_Danh_Sach_Cac_Bai_Hat();
        fragmentDiaNhac = new Fragment_Dia_Nhac();
        viewPagerAdapterPlayNhac = new ViewPagerPlaylistnhacAdapter(getSupportFragmentManager());
        viewPagerAdapterPlayNhac.addFragment(fragmentPlayDanhSachCacBaiHat);
        viewPagerAdapterPlayNhac.addFragment(fragmentDiaNhac);
        viewPager.setAdapter(viewPagerAdapterPlayNhac);
        customView();
    }
    private void customView() {
        fragmentDiaNhac = (Fragment_Dia_Nhac) viewPagerAdapterPlayNhac.getItem(1);
        if (baiHatArrayList.size() >0) {
            getSupportActionBar().setTitle(baiHatArrayList.get(0).getTenBaiHat());
            new PlayMp3().execute(baiHatArrayList.get(0).getLinkBaiHat());
            btnPlay.setImageResource(R.drawable.iconpause);
        }
    }


    class PlayMp3 extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.reset();
                }
            });

                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();//sd de phat media

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
            UpdateTime();
        }
    }
    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekbar.setMax(mediaPlayer.getDuration());
    }
private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this,300);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            play = true;
                            try{
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        },300);
    Handler handler1 = new Handler();
    handler1.postDelayed(new Runnable() {
        @Override
        public void run() {
            if(play == true){
                if(positon < (baiHatArrayList.size())){
                    btnPlay.setImageResource(R.drawable.iconpause);
                    positon++;
                    if(repeat == true){
                        if(positon==0){
                            positon = baiHatArrayList.size();
                        }
                        positon-=1;
                    }
                    if(shuffle==true){
                        Random random = new Random();
                        int index = random.nextInt(baiHatArrayList.size());
                        if(index == positon){
                            positon = index -1;
                        }
                        positon = index;
                    }
                    if (positon> (baiHatArrayList.size()-1)){
                        positon = 0;
                    }
                    new PlayMp3().execute(baiHatArrayList.get(positon).getLinkBaiHat());
                    fragmentDiaNhac.setImgDiaNhac(baiHatArrayList.get(positon).getHinhBaiHat());
                    getSupportActionBar().setTitle(baiHatArrayList.get(positon).getTenBaiHat());
            }
            btnPrev.setClickable(false);
            btnNext.setClickable(false);
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btnPrev.setClickable(true);
                    btnNext.setClickable(true);
                }
            },5000);
            play = false;
            handler1.removeCallbacks(this);
            }else{
                handler1.postDelayed(this,1000);
            }
        }
    },1000);
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        finish();
    }
    private void anhXa() {
        toolbar = findViewById(R.id.activityPlayNhac_toolBar);
        seekbar = findViewById(R.id.activityPlayNhac_seekBar);
        txtTimeStart = findViewById(R.id.activityPlayNhac_timeStart);
        txtTimeEnd = findViewById(R.id.activityPlayNhac_timeEnd);
        btnSuffle = findViewById(R.id.activityPlayNhac_btnSuffle);
        btnPrev = findViewById(R.id.activityPlayNhac_btnPrevious);
        btnPlay = findViewById(R.id.activityPlayNhac_btnPlay);
        btnNext = findViewById(R.id.activityPlayNhac_btnNext);
        btnRepeat = findViewById(R.id.activityPlayNhac_btnRepeat);
        viewPager = findViewById(R.id.activityPlayNhac_viewPager);
        repeat = false;
        shuffle = false;
        play = false;
    }

    private void DataIntent() {
        Intent intent = getIntent();
        baiHatArrayList.clear();
        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
            Baihat baihat = intent.getParcelableExtra("cakhuc");
           baiHatArrayList.add(baihat);
            }
            if(intent.hasExtra("caccakhuc")){
                ArrayList<Baihat> mangbaihat = intent.getParcelableArrayListExtra("caccakhuc");
                baiHatArrayList = mangbaihat ;
            }
        }
    }
}