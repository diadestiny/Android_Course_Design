package com.guet.shareapp;

import com.guet.shareapp.domain.SimpleRespose;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface API {

    @GET("/")
    Call<String> getHello();

    @FormUrlEncoded
    @POST("/user/login")
    Call<SimpleRespose> login(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/user/register")
    Call<SimpleRespose> register(@Field("username") String username, @Field("password") String password);
}
