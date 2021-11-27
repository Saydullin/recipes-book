package com.example.recipesbook.models;

public class Recipe {

    int id, ingredientsAmount, duration;
    String title, img, description;

    public Recipe(int id, int ingredientsAmount, int duration, String title, String img, String description) {
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

    public int getDuration() {
        return duration;
    }

    public void setDuration() {
        this.duration = duration;
    }

    public int getIngredientsAmount() {
        return ingredientsAmount;
    }

    public void setIngredientsAmount(int ingredientsAmount) {
        this.ingredientsAmount = ingredientsAmount;
    }

}
