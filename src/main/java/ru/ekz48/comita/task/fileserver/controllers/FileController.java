package ru.ekz48.comita.task.fileserver.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @GetMapping("/getNames")
    public List<String> getFileNames() {
        return null;
    }

    @PostMapping("/upload")
    public void uploadFile() {}

    @GetMapping("/getFile/{name}")
    public String getFile(@PathVariable String name) {
        return null;
    }

    @DeleteMapping("/delete/{name}")
    public void deleteFile(@PathVariable String name) {

    }


}
