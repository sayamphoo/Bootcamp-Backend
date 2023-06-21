package com.boot_camp.Boot_Camp.storages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ImageStorage {

    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    private Path imageDirectory;

    @PostConstruct
    public void init() throws IOException {
        imageDirectory = Paths.get(imageDirectoryPath);
        if (!Files.exists(imageDirectory)) {
            Files.createDirectories(imageDirectory);
        }
    }
    public Path getImageDirectory() {
        return imageDirectory;
    }
}
