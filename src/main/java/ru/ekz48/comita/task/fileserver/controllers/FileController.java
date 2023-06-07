package ru.ekz48.comita.task.fileserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.ekz48.comita.task.fileserver.services.FileStorageService;

import java.util.List;
import java.util.stream.Collectors;

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

        fileStorageService.store(file);

        return new ResponseEntity<>("file was uploaded", HttpStatusCode.valueOf(200));
    }

    @GetMapping("/getFile/{name}")
    public String getFile(@PathVariable String name) {
        return null;
    }

    @DeleteMapping("/delete/{name}")
    public void deleteFile(@PathVariable String name) {

    }


}
