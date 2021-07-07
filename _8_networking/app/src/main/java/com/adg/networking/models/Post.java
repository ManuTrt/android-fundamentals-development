package com.adg.networking.models;

import com.google.gson.annotations.SerializedName;

public class Post
{
    private int userId;
    private int id;
    private String title;
    /*
     * Annotation because the field
     * name and the json field differ
     */
    @SerializedName("body")
    private String text;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
