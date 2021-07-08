package com.adg.networking;

import com.adg.networking.models.Comment;
import com.adg.networking.models.Post;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/*
 * Here we have an interface meant to
 * represent the api for the JPH REST API SERVER;
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
     * the endpoint that the posts are at;
     */
    @GET("posts")
    Call<List<Post>> getPosts();

    /*
     * A possible endpoint may look like
     * this '/posts?userId=5' which allows
     * us to query the information based
     * on some conditions
     */
    @GET("posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);

    /*
     * Also, multiple Query parameters are
     * supported '/posts?userId=5&_sort=id&_order=desc'
     */
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") int userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    /*
     * For more flexibility in choosing the
     * query parameters we can use QueryMap
     * where the key is the query parameter
     * and the value will be the actual value
     */
    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String, String> parameters);

    /*
     * In order not to hardcode the values
     * necessary for the GET request,
     * a replacement block is mandatory
     * using curly braces;
     * It needs to be passed as an argument
     * to the method
     */
    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    /*
     * The most generic method is to pass the url
     * for the get request at runtime
     */
    @GET
    Call<List<Comment>> getComments(@Url String url);

    /*
     * In case we want to send data
     * to the server, a POST request
     * is sent having a Body that contains
     * our information;
     * GSON will convert the data from our
     * post object to the json equivalent;
     * Given the fact that a fake rest api
     * is used, the post won't actually be stored
     * on the server and it will fake its completion
     * (sending back a successful HTTP Response with
     * the whole post we sent)
     */
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    /*
     * A more flexible method is using
     * a FormUrlEncoded. This is usually used
     * in html forms or simple key value
     * pairs format, not nested ones.
     * Url encoding means that the
     * body will contain '&' between the
     * key-value pairs and also '%20'
     * instead of spaces
     */
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> fields);

    /*
     * Updating a single post on the server
     * using a placeholder for the post id
     * and a Body that will contain the new
     * values we want to change the contents of
     * the post with
     */
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id, @Body Post post);

    /*
     * Analogous to PUT is the PATCH request.
     * PUT will change the post completely with
     * what we sent over.
     * PATCH will change only the fields we
     * specify (not null ones). GSON by default
     * ignores null values so the resulting
     * json wont contain any field that previously
     * was initialized as null;
     */
    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id, @Body Post post);

    /*
     * Deleting a post
     */
    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
