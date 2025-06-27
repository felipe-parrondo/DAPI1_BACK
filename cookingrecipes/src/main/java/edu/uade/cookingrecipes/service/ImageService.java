package edu.uade.cookingrecipes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImage (MultipartFile file, String resourceIdentifier, String id);
    String readImage (String resourceIdentifier, String fileIdentifier);
}
