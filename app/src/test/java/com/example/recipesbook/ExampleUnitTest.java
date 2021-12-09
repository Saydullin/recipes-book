package com.example.recipesbook;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.recipesbook.utils.EmailValidator;


public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void emailValidator() {
        assertTrue(EmailValidator.isValid("saydullinweb@gmail.com"));
    }

}



















