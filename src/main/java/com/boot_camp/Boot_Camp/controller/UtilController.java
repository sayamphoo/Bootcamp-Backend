package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/api/v1")
public class UtilController {

    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BuyMenuRepository buyRepository;

    @Autowired
    private StatisticsMenuRepository statisticsMenuRepository;

    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String filename) throws IOException {
        String imagePath = imageDirectoryPath + "/" + filename;

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

    @DeleteMapping("all-delete")
    public String delete() {

        historyTransferRepo.deleteAll();
        storeRepository.deleteAll();
        memberRepo.deleteAll();
        statisticsMenuRepository.deleteAll();
        buyRepository.deleteAll();
        return "Success";
    }
}