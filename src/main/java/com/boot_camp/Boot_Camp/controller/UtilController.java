package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.entity.BuyMenuEntity;
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
    private SaveFileService saveFileService;
    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable(required = false) String filename) throws IOException {

        if (filename == null) {
            filename = "auto";
        }
        return saveFileService.getImages(filename);
    }

    @Autowired
    private BuyMenuRepository buyMenuRepository;
    @Autowired
    private HistoryTransferRepository historyTransferRepository;
    @Autowired
    private LockerIdRepository lockerIdRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private StatisticsMenuRepository statisticsMenuRepository;
    @Autowired
    private StoreRepository storeRepository;

    @DeleteMapping("/all-delete")
    public String delete() {
        buyMenuRepository.deleteAll();
        historyTransferRepository.deleteAll();
        lockerIdRepository.deleteAll();
        memberRepository.deleteAll();
        promotionRepository.deleteAll();
        statisticsMenuRepository.deleteAll();
        storeRepository.deleteAll();
        return "Success";
    }
}