package com.example.recipesbook.utils;

public class Validator {

    boolean isCorrect = true;

    public void checkInt(String name, int value, int[] minMax) throws Exception {

        if (value < minMax[0]) {
            throw new Exception(name + "`s value must be more than " + minMax[0]);
        }
        if (value > minMax[1] && minMax[1] != -1) {
            throw new Exception(name + "`s value must be less than " + minMax[1]);
        }

    }

    public void checkString(String name, String value, int[] minMax) throws Exception {

        if (value.length() < minMax[0]) {
            throw new Exception("The wrong length of " + name);
        }
        if (value.length() > minMax[1] && minMax[1] != -1) {
            throw new Exception("The wrong length of " + name);
        }

    }

}


