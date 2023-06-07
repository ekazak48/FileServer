package ru.ekz48.comita.task.fileserver.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(@Value("${app.fileStorage.rootFolder}") String uploadLocation) {
        this.rootLocation = Paths.get(uploadLocation);
    }

    public List<String> getFileNames() {
        return null;
    }

    public void store(MultipartFile file) {
        if (file.isEmpty()) {
            //TODO: exception, that file is empty
        }
        Path dest = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
        if (!dest.getParent().equals(this.rootLocation.toAbsolutePath())) {
            //TODO: exception, that cannot store file here
        }
        try (InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            //TODO: own exception
            throw new RuntimeException(e);
        }
    }






}
