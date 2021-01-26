package co.edu.unipiloto.restservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView list;
    private ArrayList<String> titles= new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,titles);
        list = findViewById(R.id.list);
        list.setAdapter(arrayAdapter);
        getPosts();

    }

    public void sendPost(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TextView userid = (TextView) findViewById(R.id.userid);
        TextView  id = (TextView) findViewById(R.id.txtid);
        TextView  title= (TextView) findViewById(R.id.title);
        TextView body = (TextView) findViewById(R.id.body);

        Post post = new Post();
        post.setId(Integer.parseInt(id.getText().toString()));
        post.setUserId(Integer.parseInt(userid.getText().toString()));
        post.setTitle(title.getText().toString());
        post.setBody(body.getText().toString());
        PostService postService = retrofit.create(PostService.class);
        Call<Post> call = postService.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                titles.add(response.body().toString());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    private void getPosts() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService postService = retrofit.create(PostService.class);
        Call<List<Post>> call = postService.getPost();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                for (Post post : response.body()){
                    titles.add(post.getTitle());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }
}