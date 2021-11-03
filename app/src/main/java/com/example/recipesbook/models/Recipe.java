package com.example.recipesbook.models;

public class Recipe {

    int id, ingredientsAmount;
    String title, img, duration, description;

    public Recipe(int id, int ingredientsAmount, String title,  String duration, String img, String description) {
        this.id = id;
        this.img = img;
        this.title = title;
        this.duration = duration;
        this.ingredientsAmount = ingredientsAmount;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String date) {
        this.duration = duration;
    }

    public int getIngredientsAmount() {
        return ingredientsAmount;
    }

    public void setIngredientsAmount(int ingredientsAmount) {
        this.ingredientsAmount = ingredientsAmount;
    }
}
