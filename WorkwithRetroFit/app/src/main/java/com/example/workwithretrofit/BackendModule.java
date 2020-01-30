package com.example.workwithretrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BackendModule {

    private static BackendModule sInstance;
    private final Retrofit mRetrofit;
    private final Backend mBackend;
    private List<Post> mPosts = new ArrayList<>();
    private State mState = State.IDLE;
    private Listener mListener;

    public static void createInstance() {
        sInstance = new BackendModule();
    }

    public static BackendModule getInstance() {
        return sInstance;
    }

    private BackendModule() {
        mRetrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        mBackend = mRetrofit.create(Backend.class);
    }

    void changeState(final State newState) {
        mState = newState;
        if (mListener != null) {
            mListener.onStateChanged(mState);
        }
    }

    public void loadPosts() {
        if (mState != State.IDLE) {
            return;
        }
        changeState(State.LOADING);
        mBackend.listPost().enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                mPosts = response.body();
                if (mListener != null) {
                    mListener.onPostLoader(response.body());
                }
                changeState(State.IDLE);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                if (mListener != null) {
                    mListener.onPostLoadingFailed(t);
                }
                changeState(State.IDLE);
            }
        });
    }

    public void setListener(final Listener listener) {
        mListener = listener;
        if (mListener != null) {
            mListener.onStateChanged(mState);
        }
    }

    public List<Post> getPosts() {
        return mPosts;
    }

    public enum State {
        IDLE, LOADING
    }

    public interface Listener {

        void onStateChanged(State state);
        void onPostLoader(List<Post> posts);
        void onPostLoadingFailed(Throwable t);
    }
}
