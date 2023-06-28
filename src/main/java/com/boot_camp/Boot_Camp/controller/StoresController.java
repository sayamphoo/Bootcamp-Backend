package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.repository.*;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.MembersService;
import com.boot_camp.Boot_Camp.services.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MenuStoreDomain getDetailStore(
            @RequestParam("id") String idLocker) {

        String id = utilService.getIdRecord(idLocker); // id shop
        return storesService.getStoresDetail(id);
    }

    @PutMapping("/set-to-store")
    public UtilDomain toStore(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getAttribute("id").toString();
        return storesService.toStore(id);
    }

    @PostMapping(value = "/upload-menu", consumes = "multipart/form-data")
    public UtilDomain uploadMenu(
            @RequestParam("name") String name,
            @RequestParam("price") int price,
            @RequestParam("exchange") int exchange,
            @RequestParam("receive") int receive,
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") int category,
            HttpServletRequest req) throws Exception {

        String id = req.getAttribute("id").toString();

        return storesService.uploadMenu(id, name, price, exchange, receive, file, category);

    }

    @GetMapping("/get-menu-category")
    public Set<AllStoresDomain> getMenuCategory(
            @RequestParam("category") int category) {
        return storesService.getStoreCategory(category);
    }

    @PostMapping(value = "/upload-picture-stores", consumes = "multipart/form-data")
    public UtilDomain uploadPictureStore(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest req) throws Exception {
        String id = req.getAttribute("id").toString();
        return storesService.uploadPictureStore(id, file);
    }


    //-----------------------------------------------------------------------------
}
