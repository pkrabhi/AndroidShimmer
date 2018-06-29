package com.example.abhi.androidshimmer.Retrofit;

import com.example.abhi.androidshimmer.Model.Model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IMyApi {

    @GET("photos")
    Observable<List<Model>> getData();
}
