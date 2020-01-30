package com.example.workwithretrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final BackendModule mBackendModule = BackendModule.getInstance();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private PostsAdapter mPostsAdapter;

    final BackendModule.Listener mListener = new BackendModule.Listener() {

        @Override
        public void onStateChanged(BackendModule.State state) {
            switch (state) {
                case IDLE:
                    mSwipeRefreshLayout.setRefreshing(false);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mPostsAdapter.setPosts(mBackendModule.getPosts());
                    break;
                case LOADING:
                    mRecyclerView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setRefreshing(true);
                    break;
            }
        }

        @Override
        public void onPostLoader(List<Post> posts) {
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT);
        }

        @Override
        public void onPostLoadingFailed(Throwable t) {
            Toast.makeText(MainActivity.this, "Error loading posts", Toast.LENGTH_SHORT);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = findViewById(R.id.swipe_view);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mBackendModule.loadPosts();
            }
        });

        mPostsAdapter = new PostsAdapter();
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mPostsAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBackendModule.setListener(mListener);
    }

    class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

        final LayoutInflater mLayoutInflater = LayoutInflater.from(MainActivity.this);
        List<Post> mPosts = new ArrayList<>();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(mLayoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(mPosts.get(position).mBody);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }

        public void setPosts(final List<Post> posts) {
            mPosts = posts;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
