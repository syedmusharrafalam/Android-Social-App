package com.socialapp;

import android.graphics.Bitmap;

/**
 * Created by User on 2/24/2018.
 */

public class User {
    private String id;
    private String userName;
    private String postText;
    private String image;
    private int like;
    private String getPostId;
public User(){

}

    public User(String id, String userName, String postText, String image,int like,String getPostId) {
        this.id = id;
        this.userName = userName;
        this.postText = postText;
        this.image = image;
        this.like=like;
        this.getPostId=getPostId;
    }

    public String getGetPostId() {
        return getPostId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostText() {
        return postText;
    }

    public String getImage() {
        return image;
    }

    public String getId() {
        return id;
    }

    public int getLike() {
        return like;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setGetPostId(String getPostId) {
        this.getPostId = getPostId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
