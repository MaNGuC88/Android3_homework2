package com.example.lesson2.data.remote;

import com.example.lesson2.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HerokuApi {

    @GET("/posts?sort=asc")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createPost(
            @Body Post post
    );

    @DELETE("/posts/{id}")
    Call<Void> deletePost(
            @Path("id") int id
    );

    @PUT("/posts/{id}")
    Call<Post> updatePost(
            @Path("id") int id,
            @Body Post post
    );

}
