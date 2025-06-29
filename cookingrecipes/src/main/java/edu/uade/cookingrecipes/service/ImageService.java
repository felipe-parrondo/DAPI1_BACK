package edu.uade.cookingrecipes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    //void saveImage (MultipartFile file, String resourceIdentifier, String id);
    void saveFile (MultipartFile file, String id, String resourceIdentifier) throws IOException;
    String readImage (String resourceIdentifier, String fileIdentifier);
}
