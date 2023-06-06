package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.UtilStoreDomain;
import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
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

@RestController
@RequestMapping(value = "/api/v1/stores")
public class StoresController {
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
        System.out.println(id);
        return storesService.getStoresDetail(id);
    }

    @PutMapping("/set-to-store")
    public UtilStoreDomain toStore(HttpServletRequest req, HttpServletResponse res) {
        String id = req.getAttribute("id").toString();
        Boolean isStore = storesService.toStore(id);
        UtilStoreDomain utilStoreDomain = new UtilStoreDomain();
        if (isStore) {
            res.setStatus(HttpStatus.OK.value());
            utilStoreDomain.setMessage(HttpStatus.OK.getReasonPhrase());
        } else {
            res.setStatus(HttpStatus.NOT_FOUND.value());
            utilStoreDomain.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
        }

        utilStoreDomain.setStatus(isStore);

        return utilStoreDomain;
    }

    @PostMapping(value = "/upload-menu", consumes = "multipart/form-data")
    public UtilStoreDomain upload(@RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("exchange") int exchange, @RequestParam("receive") int receive, @RequestParam("file") List<MultipartFile> file, HttpServletRequest req,HttpServletResponse res) throws Exception {
        String id = req.getAttribute("id").toString();
        storesService.store(id, name, price, exchange, receive, file);

        res.setStatus(HttpStatus.OK.value());
        UtilStoreDomain utilStoreDomain = new UtilStoreDomain();
        utilStoreDomain.setStatus(true);
        utilStoreDomain.setMessage(HttpStatus.OK.getReasonPhrase());
        return utilStoreDomain;

    }

    @PostMapping(value = "/upload-picture-stores", consumes = "multipart/form-data")
    public UtilStoreDomain uploadPictureStore(@RequestParam("file") MultipartFile file, HttpServletRequest req,HttpServletResponse res) throws Exception {
        String id = req.getAttribute("id").toString();
        storesService.uploadPictureStore(id, file);

        res.setStatus(HttpStatus.OK.value());
        UtilStoreDomain utilStoreDomain = new UtilStoreDomain();
        utilStoreDomain.setStatus(true);
        utilStoreDomain.setMessage(HttpStatus.OK.getReasonPhrase());

        return utilStoreDomain;
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
