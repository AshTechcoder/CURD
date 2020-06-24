package com.example.curd.Model;

public class ReadModel {

    private String imageUrl, bookTitle, bookAuthor, bookDescription;
    private  double bookRating;


    public ReadModel(String imageUrl, String bookTitle, String bookAuthor, String bookDescription) {
        this.imageUrl = imageUrl;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
//        this.bookRating = bookRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

//    public double getBookRating() {
//        return bookRating;
//    }
//
//    public void setBookRating(double bookRating) {
//        this.bookRating = bookRating;
//    }
}
