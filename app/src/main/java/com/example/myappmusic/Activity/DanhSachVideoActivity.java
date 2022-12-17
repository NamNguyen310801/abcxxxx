package com.example.myappmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myappmusic.Adapter.VideoYouTubeAdapter;
import com.example.myappmusic.Model.VideoYouTube;
import com.example.myappmusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhSachVideoActivity extends AppCompatActivity {
    public static String API_KEY = "AIzaSyBKNDrI6ZrF9A_Wi-J1FjiF80HIYld1erQ";
    public static String ID_PLAYLIST = "PLVBg8hl_UcKl9wDwmbtViDjH3_2Nkrn6g";
    public static String urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+ID_PLAYLIST+"&key="+API_KEY+"&maxResults=50";
    ListView listView;
    ArrayList<VideoYouTube> arrayList;
    VideoYouTubeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_video);
        anhXa();
        GetJsonYouTube(urlGetJson);
    }
    private void anhXa() {
        listView = findViewById(R.id.listViewVideo);
        arrayList = new ArrayList<>();
        adapter = new VideoYouTubeAdapter(this, R.layout.row_video_youtube,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DanhSachVideoActivity.this,PlayVideoActivity.class);
                intent.putExtra("idvideoyoutube",arrayList.get(i).getIdVideo());
                startActivity(intent);
            }
        });
    }

    private  void GetJsonYouTube(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray("items");
                    String title= "";
                    String url= "";
                    String idVideo= "";
                    for (int i =0; i <jsonItems.length(); i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");
                        title = jsonSnippet.getString("title");

                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");
                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                        url = jsonMedium.getString("url");

                        JSONObject jsonResourceId = jsonSnippet.getJSONObject("resourceId");
                        idVideo = jsonResourceId.getString("videoId");

                        //add vao mang
                        arrayList.add(new VideoYouTube(title,url,idVideo));

                    }
                    adapter.notifyDataSetChanged();//cap nhat du lieu
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DanhSachVideoActivity.this, "loi",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}