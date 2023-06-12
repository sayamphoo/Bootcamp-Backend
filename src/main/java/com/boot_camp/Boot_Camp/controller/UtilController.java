package com.boot_camp.Boot_Camp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping(value = "/api/v1")
public class UtilController {

    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
        String imagePath = imageDirectoryPath + "/" + filename;
//        InputStream imageStream = new FileInputStream(imagePath);
//        InputStreamResource inputStreamResource = new InputStreamResource(imageStream);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .contentLength(imageStream.available())
//                .body(inputStreamResource);


        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Image Not false");
        }

        InputStream imageStream = new FileInputStream(imageFile);
        InputStreamResource inputStreamResource = new InputStreamResource(imageStream);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(imageFile.length())
                .body(inputStreamResource);
    }
}