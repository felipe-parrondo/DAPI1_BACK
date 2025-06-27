package edu.uade.cookingrecipes.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;
/*
@Service
public class ImageServiceImpl {
}



@PostMapping("/cargarMultimedia/{idReceta}")
public ResponseEntity<?> cargarMultimedia(@RequestBody List<MultipartFile> files,
                                          @PathVariable Integer idReceta){
    System.out.println("------------------------");
    System.out.println(files);
    if(files != null && files.size() != 0){
        List<String> fallos = RecetaController.getInstancia().cargarMultimedia(files, idReceta);
        if (fallos.size() > 0) {
            if (fallos.get(0).equals("notFound")) {
                String[] retorno = new String[]{};
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
            }
            else
                return ResponseEntity.badRequest().body(fallos);
        }
        else
            return ResponseEntity.status(HttpStatus.OK).body(fallos);
    }
    else{
        String[] retorno = new String[] {};
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(retorno);
    }

}
private UploadFileService uploadFileService = new UploadFileService();
public List<String> cargarMultimedia(List<MultipartFile> files, Integer idReceta){
    Optional<Receta> oReceta = recetaService.findById(idReceta);
    List<String> fallos = new ArrayList<>();
    if (oReceta.isPresent()) {

        for(MultipartFile file: files) {
            try {
                uploadFileService.saveFile(file, idReceta);
            }
            catch (Exception e) {
                fallos.add(file.getOriginalFilename());
            }
        }
        if(fallos.size() > 0) {
            Receta receta = oReceta.get();
            receta.setEstado(Receta.Estado.ErrorImagenes);
            recetaService.save(receta);
        }
    }
    else{
        fallos.add("notFound");
    }
    return fallos;
};@Service
public class UploadFileService {


    private String UPLOAD_FOLDER = ".//src//main//resources//multimedia//";

    public String saveFile(MultipartFile file, Integer idReceta) throws IOException{
        File directorio = new File(UPLOAD_FOLDER + idReceta.toString());
        if (!directorio.exists())
            directorio.mkdirs();

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_FOLDER  + idReceta.toString() + "//" +  file.getOriginalFilename());
        Files.write(path,bytes);

        return   file.getOriginalFilename();
    }
}*/