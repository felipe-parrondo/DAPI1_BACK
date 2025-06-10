package edu.uade.cookingrecipes.service;

public interface EmailService {

    void sendEmail (String target, String subject, String body);

}
