package com.example.workwithretrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Backend {

    @GET("/comments")
    Call<List<Post>> listPost();
}
