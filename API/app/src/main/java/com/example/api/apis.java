package com.example.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface apis {

    @POST("users")

        //on below line we are creating a method to post our data.
    Call<Networkclient> createPost(@Body Networkclient dataModal);
}
