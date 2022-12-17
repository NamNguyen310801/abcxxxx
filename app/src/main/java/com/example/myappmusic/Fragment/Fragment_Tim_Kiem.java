package com.example.myappmusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myappmusic.Adapter.SearchBaiHatAdapter;
import com.example.myappmusic.Model.Baihat;
import com.example.myappmusic.R;
import com.example.myappmusic.Service.ApiService;
import com.example.myappmusic.Service.DataService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Tim_Kiem extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView txtKoCoDuLieu;

    private ArrayList<Baihat> baiHatArrayList;
    SearchBaiHatAdapter adapter;

    public Fragment_Tim_Kiem() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        mapping();
        init();
        return view;
    }

    private void init() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getListBaiHatTimKiem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getListBaiHatTimKiem(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void getListBaiHatTimKiem(String keyword) {
        if ( baiHatArrayList != null) {
            baiHatArrayList.clear();
            recyclerView.setAdapter(null);
        }
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callback = dataService.GetSearchBaihat(keyword);
        callback.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                baiHatArrayList = (ArrayList<Baihat>) response.body();
                if (baiHatArrayList.size() > 0) {
                    adapter = new SearchBaiHatAdapter(getActivity(), baiHatArrayList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                    txtKoCoDuLieu.setText("");
                    txtKoCoDuLieu.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    recyclerView.setVisibility(View.GONE);
                    txtKoCoDuLieu.setVisibility(View.VISIBLE);
                    txtKoCoDuLieu.setText("Không tìm thấy bài hát");
                }
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {
                Log.d("tag", "Load data baihat fail");
            }
        });
    }

    private void mapping() {
        toolbar = view.findViewById(R.id.fmTimKiem_toolbar);
        recyclerView = view.findViewById(R.id.fmTimKiem_recyclerView);
        txtKoCoDuLieu = view.findViewById(R.id.fmTimKiem_txtKhongCoDuLieu);
    }
}
