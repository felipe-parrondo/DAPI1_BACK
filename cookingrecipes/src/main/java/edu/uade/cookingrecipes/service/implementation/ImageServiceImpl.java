package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.exceptions.NotFoundException;
import edu.uade.cookingrecipes.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Service
public class ImageServiceImpl implements ImageService {

    private final String UPLOAD_FOLDER = ".//uploads//multimedia";

    /*public void saveImage (MultipartFile file, String resourceIdentifier, String id) { //TODO baseRoute inside the service
        try {
            Files.createDirectories(Paths.get(BASE_ROUTE, resourceIdentifier));
            byte[] bytes = file.getBytes();
            Path path = Paths.get(BASE_ROUTE, resourceIdentifier, id, file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException ignored) {
            System.out.println(ignored);
            throw new NotFoundException("");
        }

    }*/
    public void saveFile(MultipartFile file, String id, String resourceIdentifier) throws IOException{
        File directorio = new File(String.valueOf(Paths.get(UPLOAD_FOLDER, resourceIdentifier, id)));
        if (!directorio.exists())
            directorio.mkdirs();

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_FOLDER, resourceIdentifier, id, file.getOriginalFilename());
        Files.write(path,bytes);

       // return   file.getOriginalFilename();
    }

    /**
     * @param resourceIdentifier
     * @param fileIdentifier
     * @return Base64 String representing the image that was requested
     * @throws IOException
     */
    public String readImage (String resourceIdentifier, String fileIdentifier) {
        try {
            File directory = new File(Paths.get(UPLOAD_FOLDER, resourceIdentifier).toString());
            byte[] bytes = Files.readAllBytes(Paths.get(UPLOAD_FOLDER, resourceIdentifier, fileIdentifier));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            return "";
        }
    }
}