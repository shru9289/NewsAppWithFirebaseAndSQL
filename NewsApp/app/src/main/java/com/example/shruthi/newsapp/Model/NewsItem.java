package com.example.shruthi.newsapp.Model;

/**
 * Created by Shruthi on 6/26/2017.
 */

public class NewsItem {

    private String title;
    private String author;
    private String description;
    private String published;
    private String imageUrl;
    private String url;



    public NewsItem(String title, String description, String imageUrl, String url, String author, String published) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.url=url;
        this.author=author;
        this.published=published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // Shruthi -- earlier Newsitem did not have imageUrl to fetch it is added here
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
