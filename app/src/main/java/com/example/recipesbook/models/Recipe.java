package com.example.recipesbook.models;

public class Recipe {

    long duration;
    String description, id, image, ingredients, tag, title, userEmail, userName;

    public Recipe(
            String description,
            long duration,
            String id,
            String image,
            String ingredients,
            String tag,
            String title,
            String userEmail,
            String userName
    ) {
        this.id = id;
        this.tag = tag;
        this.image = image;
        this.title = title;
        this.duration = duration;
        this.userEmail = userEmail;
        this.userName = userName;
        this.ingredients = ingredients;
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String img) {
        this.image = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getDuration() {
//        StringBuilder durationFormat = new StringBuilder();
//
//        if (duration / 60 >= 1) {
//            durationFormat.append(duration / 60).append("h ");
//        }
//        if (duration % 60 >= 1) {
//            durationFormat.append(duration % 60).append("min");
//        }
//
//        return durationFormat.toString();
//    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
