package com.socialapp;

/**
 * Created by User on 3/3/2018.
 */

public class Comments {

    private String userName;
    private String comments;

 public Comments()
 {

 }

    public Comments(String userName, String comments) {

        this.userName = userName;
        this.comments = comments;
    }



    public String getUserName() {
        return userName;
    }

    public String getComments() {
        return comments;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
