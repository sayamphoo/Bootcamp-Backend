package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.repository.*;
import com.boot_camp.Boot_Camp.services.SaveFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/v1/util")
public class UtilController {

    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private StoreRepository storeRepo;

    @Autowired
    private SaveFileService saveFileService;
    @Autowired
    private StatisticsMenuRepository statisticsMenuRepo;

    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable(required = false) String filename) throws IOException {

        if (filename == null) {
            filename = "auto";
        }
        return saveFileService.getImages(filename);
    }


    @DeleteMapping("/all-delete")
    public String delete() {

        historyTransferRepo.deleteAll();
        storeRepo.deleteAll();
        memberRepo.deleteAll();
        statisticsMenuRepo.deleteAll();
        return "Success";
    }
}