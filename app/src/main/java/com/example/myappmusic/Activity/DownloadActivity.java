package com.example.myappmusic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;


import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.myappmusic.R;
import com.squareup.picasso.Picasso;


public class DownloadActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIN_CODE = 10 ;
    ImageView imageView;
    TextView txtTenbaihat, txtTencasi;
    Button button;
    Toolbar toolbar;
    String tenbaihat="";
    String hinhbaihat="";
    String linkbaihat="";
    String casi="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        getData();

        toolbar = findViewById(R.id.toolbardownload);
        txtTenbaihat = findViewById(R.id.textbaihat);
        txtTencasi = findViewById(R.id.textviewcasi);
        button = findViewById(R.id.btn_dl);
        imageView = findViewById(R.id.imageviewbaihat);
        button.setOnClickListener(view -> checkPermission(linkbaihat));
        txtTenbaihat.setText(tenbaihat);
        txtTencasi.setText(casi);
        Picasso.get().load(hinhbaihat).into(imageView);

    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bun = intent.getExtras();
        tenbaihat = bun.getString("tenbaihatdownload");
        casi = bun.getString("tencasidownload");
        hinhbaihat = bun.getString("hinhdownload");
        linkbaihat = bun.getString("linkdownload");
    }

    public  void checkPermission(String urlFile) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                String permission = (Manifest.permission.WRITE_EXTERNAL_STORAGE);
                requestPermissions(new String[]{permission},REQUEST_PERMISSIN_CODE);
            }else{
                startDownloadFile(urlFile);
                Toast.makeText(this, "Bài hát "+ tenbaihat+" đang được tải", Toast.LENGTH_SHORT).show();
            }
        }else{
            startDownloadFile(urlFile);
            Toast.makeText(this, "Bài hát "+ tenbaihat+" đang được tải", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIN_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloadFile(linkbaihat);
                Toast.makeText(this, "Bài hát "+ tenbaihat+" đang được tải", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void startDownloadFile(String urlFile) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlFile));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download");
        request.setDescription("Download file...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.valueOf(System.currentTimeMillis()));
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if(downloadManager != null){
            downloadManager.enqueue(request);
        }
    }

}