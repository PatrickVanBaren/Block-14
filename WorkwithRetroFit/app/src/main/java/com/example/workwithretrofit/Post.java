package com.example.workwithretrofit;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("postId")
    int mPostId;
    @SerializedName("id")
    int mId;
    @SerializedName("name")
    String mName;
    @SerializedName("email")
    String mEmail;
    @SerializedName("body")
    String mBody;

    public Post(final int postId, final int id, final String name, final String email, final String body) {
       mPostId = postId;
        mId =  id;
        mName = name;
        mEmail = email;
        mBody = body;
    }
}
