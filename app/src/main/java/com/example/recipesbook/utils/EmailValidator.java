package com.example.recipesbook.utils;

import android.content.Context;

public class EmailValidator {

    Context context;

    public EmailValidator(Context context) {
        this.context = context;
    }

    public static boolean isValid(String email) {
        return true;
    }

}
