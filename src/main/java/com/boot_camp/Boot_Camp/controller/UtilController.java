package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.repository.*;
import com.boot_camp.Boot_Camp.services.SaveFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/util")
public class UtilController {

    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private SaveFileService saveFileService;
    @Autowired
    private StatisticsMenuRepository statisticsMenuRepository;

    @Value("${app.image.directory}")
    private String imageDirectoryPath;

    @GetMapping(value = "/image/{filename}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
        return saveFileService.getImages(filename);
    }

//    @PostMapping("/dsdfsdf")
//    public ResponseEntity<Map<String,Integer>> getHH(@RequestBody Map<String,Integer> list) {
//        return  ResponseEntity.ok().body(list);
//    }

    @DeleteMapping("/all-delete")
//    @PostConstruct
    public String delete() {

        historyTransferRepo.deleteAll();
        storeRepository.deleteAll();
        memberRepo.deleteAll();
        statisticsMenuRepository.deleteAll();
        return "Success";
    }
}