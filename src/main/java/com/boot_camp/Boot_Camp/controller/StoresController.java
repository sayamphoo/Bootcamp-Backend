package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.HashDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilStoreDomain;
import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.*;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import com.boot_camp.Boot_Camp.services.stroes.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/v1/stores")
public class StoresController {

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


    @Autowired
    private StoresService storesService;
    @Autowired
    private MembersService membersService;

    @Autowired
    private UtilService utilService;


    @GetMapping("/get-all-stores")
    public List<AllStoresDomain> allStoresDomain() {
        return membersService.getAllStores();
    }

    @GetMapping("/get-detail-store")
    public MenuStoreDomain getDetailStore(@RequestParam("id") String id) {
        id = utilService.searchDatabaseID(id);
        return storesService.getStoresDetail(id);
    }

    @PutMapping("/set-to-store")
    public UtilStoreDomain toStore(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getAttribute("id").toString();
        return storesService.toStore(id);
    }

    @PostMapping(value = "/upload-menu", consumes = "multipart/form-data")
    public UtilStoreDomain uploadMenu(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("exchange") int exchange,
            @RequestParam("receive") int receive,
            @RequestParam("file") List<MultipartFile> file,
            @RequestParam("category") int category,
            HttpServletRequest req) throws Exception {

        String id = req.getAttribute("id").toString();

        return storesService.uploadMenu(id, name, price, exchange, receive, file, category);

    }

    @DeleteMapping("/delete-menu")
    public UtilStoreDomain menuDelete(@RequestParam("idMenu") String idMenu, HttpServletRequest req) {
        String id = req.getAttribute("id").toString();
        System.out.println(id);
        System.out.println(idMenu);
        return storesService.deleteMenu(idMenu);
    }

    @GetMapping("/get-menu-category")
    public Set<AllStoresDomain> getMenuCategory(
            @RequestParam("category") int category) {
        return storesService.getMenuCategory(category);
    }

    @PostMapping(value = "/upload-picture-stores", consumes = "multipart/form-data")
    public UtilStoreDomain uploadPictureStore(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String id = req.getAttribute("id").toString();
        return storesService.uploadPictureStore(id, file);
    }

    @GetMapping("/get-hash-menu-qrcode")
    public HashDomain getHashMenuQrcode(@RequestParam("id") String idStore, @RequestParam("point") int point, HttpServletRequest req) {
        String id = req.getAttribute("idAccount").toString();
        System.out.println(id);
        return storesService.getHashMenuQrcode(id, idStore, point);
    }


    //-----------------------------------------------------------------------------
    @GetMapping("/show")
    public Iterable<StoreMenuEntity> show() {
        return storesService.getAll();
    }

    @GetMapping("/delete")
    public void delete() {
        historyTransferRepo.deleteAll();
        storeRepository.deleteAll();
        memberRepo.deleteAll();
        statisticsMenuRepository.deleteAll();
        buyRepository.deleteAll();
    }
}
