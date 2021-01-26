package co.edu.unipiloto.restservice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {
    String API_ROUTE = "/posts";
    @GET(API_ROUTE)
    Call<List<Post>> getPost();

    @GET(API_ROUTE+"/{id}")
    Call<List<Post>> getOnePost(@Path("id") int id);

    @POST(API_ROUTE)
    Call<Post> createPost(@Body Post post);





}
