package com.example.recipesbook.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Recipe {

    long duration, date;
    String description, id, image, ingredients, tag, title, userEmail, userName;

    public Recipe(
            String description,
            long duration,
            long date,
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
        this.date = date;
        this.image = image;
        this.title = title;
        this.duration = duration;
        this.userEmail = userEmail;
        this.userName = userName;
        this.ingredients = ingredients;
        this.description = description;
    }

    public long getDateLong() {
        return date;
    }

    public String getDate() {
        Date netDate = new Date(System.currentTimeMillis());
        SimpleDateFormat sfd = new SimpleDateFormat("dd MMMM', at' HH:mm", Locale.forLanguageTag("en"));
        return sfd.format(netDate);
    }

    public void setDate(long date) {
        this.date = date;
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

    public long getLongDuration() {
        return duration;
    }

    public String getDuration() {
        StringBuilder durationFormat = new StringBuilder();
        String pluralHours = duration / 60 > 1 ? "hours " : "hour ";
        String pluralMinutes = duration % 60 > 1 ? "minutes" : "minute";

        if (duration / 60 >= 1) {
            durationFormat.append(duration / 60).append(" ").append(pluralHours);
        }
        if (duration % 60 >= 1) {
            durationFormat.append(duration % 60).append(" ").append(pluralMinutes);
        }

        return durationFormat.toString();
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
