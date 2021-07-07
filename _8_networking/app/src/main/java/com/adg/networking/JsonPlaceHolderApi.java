package com.adg.networking;

import com.adg.networking.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/*
 * Here we have an interface meant to
 * represent the api for the JPH;
 * It contains the definitions of the
 * methods that will later be implemented
 * by Retrofit (annotations will help
 * retrofit know what code to complete
 * the methods with);
 * The getPosts method returns a call
 * that has a list of posts with it.
 */

public interface JsonPlaceHolderApi {
    /*
     * 'posts' is the relative url to
     * the endpoint that the posts are at
     */
    @GET("posts")
    Call<List<Post>> getPosts();
}
