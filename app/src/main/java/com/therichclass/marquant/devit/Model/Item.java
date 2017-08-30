package com.therichclass.marquant.devit.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by marquant on 8/28/2017.
 */

public class Item {
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("followers_url")
    @Expose
    private String followersUrl;
    @SerializedName("repos_url")
    @Expose
    private String reposUrl;


    public Item(String login, String avatarUrl, String htmlUrl,String followersUrl,String  reposUrl ){
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.followersUrl = followersUrl;
        this.reposUrl = reposUrl;
    }

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getAvatarUrl(){
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }


    public String getFollowersUrl(){
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl){
        this.followersUrl = followersUrl;
    }

    public String getReposUrl(){
        return reposUrl;
    }
    public void setReposUrl(String reposUrl){
        this.reposUrl = reposUrl;
    }

    public String getHtmlUrl(){
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl){
        this.htmlUrl = htmlUrl;
    }
}
