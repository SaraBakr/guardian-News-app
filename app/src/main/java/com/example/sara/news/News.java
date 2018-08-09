package com.example.sara.news;

public class News {
    String title;
    String author;
    String newsLink;
    String date;
    String section;
    String image;

    public News(String title, String author, String newsLink, String date, String section, String image) {
        this.title = title;
        this.author = author;
        this.newsLink = newsLink;
        this.date = date;
        this.section = section;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }


    public String getImage() {
        return image;
    }

}


