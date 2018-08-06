package com.example.noor.guardian;

public class News {
    private String Title;
    private String Date;
    private String Url;
    private String Author;
    private String SectionName;
    private String Image;


    public News(String title, String date, String url, String author, String sectionName, String image) {
        Title = title;
        Date = date;
        Url = url;
        Author = author;
        SectionName = sectionName;
        Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getSectionName() {
        return SectionName;
    }

    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }
}
