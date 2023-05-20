package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import com.boot_camp.Boot_Camp.services.stroes.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/stores")
public class StoresController {
    @Autowired
    private StoresService storesService;
    @Autowired
    private MembersService membersService;

    @Autowired
    private UtilService utilService;


    @PostMapping(value = "/upload-menu", consumes = "multipart/form-data")
    public int upload(@RequestParam("name") String name,
                      @RequestParam("price") int price,
                      @RequestParam("point") int point,
                      @RequestParam("file") List<MultipartFile> file,
                      HttpServletRequest req) throws Exception {
        String id = req.getAttribute("id").toString();
        storesService.store(id, name, price, point, file);
        return 0;
    }

    @PostMapping(value = "/upload-picture-stores", consumes = "multipart/form-data")
    public int uploadPictureStore(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws Exception {
        String id = req.getAttribute("id").toString();
        storesService.uploadPictureStore(id, file);
        return 0;
    }


    @GetMapping("/get-all-stores")
    public List<AllStoresDomain> allStoresDomain() {
        return membersService.getAllStores();
    }

    @GetMapping("/get-detail-store")
    public MenuStoreDomain getDetailStore(@RequestParam("id") String id) {
        id = utilService.searchDatabaseID(id);
        System.out.println(id);
        return storesService.getStoresDetail(id);
    }

    //-----------------------------------------------------------------------------
    @GetMapping("/show")
    public Iterable<StoreMenuEntity> show() {
        return storesService.getAll();
    }

    @GetMapping("/delete")
    public void delete() {
        storesService.deleteAll();
    }
}
