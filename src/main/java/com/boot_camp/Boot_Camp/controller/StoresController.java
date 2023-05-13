package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import com.boot_camp.Boot_Camp.services.stroes.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/stores")
public class StoresController {
    @Autowired
    private StoresService storesService;

    @Autowired
    private MembersService membersService;

//    @PostMapping(value = "/upload", consumes = "multipart/form-data")
//    public int upload(@RequestParam("name") String name,
//                      @RequestParam("file") List<MultipartFile> file,
//                      HttpServletRequest req) throws Exception {
//        String verify = req.getHeader("verify");
//        storesService.store(verify, name, file);
//        return (file.size());
//    }


    @GetMapping("/get-all-stores")
    public List<AllStoresDomain> allStoresDomain () {
        return membersService.getAllStores();
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
