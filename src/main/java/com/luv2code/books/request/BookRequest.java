package com.luv2code.books.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

/*
A DTO in Java stands for Data Transfer Object — it’s a simple Java object whose
main purpose is to carry data between layers of an application without containing any business logic
in my chat Fix method under java project d287
@size , @min, @ max are validators to ensure that object creation follow some rules
 */
public class BookRequest {
    private String title;
    private String author;
    private String category;
    private int rating;

    public BookRequest(String title, String author, String category, int rating) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }

    @Size (min=1 , max=30, message = "author is between  1 and 60 characters")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Size (min=1 , max=30, message = "author is between  1 and 100 characters")
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }


    @Size (min=1 , max=30, message = "category is between  1 and 30 characters")
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    @Min(value=1 , message = "Rating Must be at least 1") //data Validation
    @Max(value=5, message = "Rating Must be at most 5")
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }



}
