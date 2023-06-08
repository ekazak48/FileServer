package ru.ekz48.comita.task.fileserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.ekz48.comita.task.fileserver.services.FileStorageService;

import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileStorageService fileStorageService;

    public FileController(@Autowired FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> getFileNames() {
        List<String> fileList = fileStorageService.listAll().map(path -> path.getFileName().toString()).toList();
        return new ResponseEntity<>(fileList, HttpStatusCode.valueOf(200));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try {
            fileStorageService.store(file);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("error: " + e.getMessage(), HttpStatusCode.valueOf(400));
        }

        return new ResponseEntity<>("file was uploaded", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getFile/{name:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String name) {
        Resource file;
        try {
            file = fileStorageService.loadAsResource(name);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "" + "\"")
                    .body(file);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatusCode.valueOf(404)).build();
        }
    }

    @DeleteMapping("/delete/{name:.+}")
    public void deleteFile(@PathVariable String name) {
        fileStorageService.deleteFile(name);
    }


}
