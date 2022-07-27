package com.example.myapplication;



import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface apiCall {
    static String BASE_URL="https://wifiserver01.herokuapp.com/generate-heatmap";

    @Multipart
    @POST(BASE_URL)
    Call<ResponseBody> addRecord(@Part("Base64") MultipartBody.Part base64image, @Part("file") String file);

}
