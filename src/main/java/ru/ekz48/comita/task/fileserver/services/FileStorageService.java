package ru.ekz48.comita.task.fileserver.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path rootLocation;

    public FileStorageService(@Value("${app.fileStorage.rootFolder}") String uploadLocation) {
        this.rootLocation = Paths.get(uploadLocation);
    }

    public Stream<Path> listAll() {
        try {
            return Files
                    .walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Resource loadAsResource(String filename) throws RuntimeException {
        Path file = rootLocation.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else throw new RuntimeException("Не получилось получить содержимое файла");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Не получилось найти файл");
        }
    }

    public void store(MultipartFile file) throws RuntimeException {
        if (file.isEmpty()) {
            throw new RuntimeException("Файл пуст, не получилось загрузить");
        }
        Path dest = this.rootLocation.resolve(Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();
        if (!dest.getParent().equals(this.rootLocation.toAbsolutePath())) {
            throw new RuntimeException("Путь сохранения файла не существует");
        }
        try (InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка при загрузке файла на сервер");
        }
    }

    public void deleteFile(String fileName) {
        Path file = rootLocation.resolve(fileName);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Произошла ошибка удаления файла");
        }


    }






}
