package com.example.abhi.androidshimmer;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.abhi.androidshimmer.Adapter.MyAdapter;
import com.example.abhi.androidshimmer.Model.Model;
import com.example.abhi.androidshimmer.Retrofit.IMyApi;
import com.example.abhi.androidshimmer.Retrofit.RetrofitClient;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    IMyApi iMyApi;
    RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ShimmerLayout shimmerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init API
        Retrofit retrofit = RetrofitClient.getInstance();
        iMyApi = retrofit.create(IMyApi.class);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_to_refresh);

        shimmerLayout = (ShimmerLayout)findViewById(R.id.shimmerLayout);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                //fetch data
                fetchData();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);

                //fetch data
                fetchData();
            }
        });
    }

    private void fetchData() {
        shimmerLayout.startShimmerAnimation();

        compositeDisposable.add(iMyApi.getData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<Model>>() {
            @Override
            public void accept(List<Model> models) throws Exception {
                displayData(models);


            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }));

        swipeRefreshLayout.setRefreshing(false);
    }

    private void displayData(List<Model> models) {
        MyAdapter adapter = new MyAdapter(this,models);
        recyclerView.setAdapter(adapter);
        shimmerLayout.stopShimmerAnimation();
        shimmerLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
