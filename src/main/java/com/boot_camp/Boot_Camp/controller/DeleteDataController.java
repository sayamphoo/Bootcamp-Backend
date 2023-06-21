package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.services.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/secure/data")
public class DeleteDataController {
    @Autowired
    DeleteService deleteService;

    @DeleteMapping("/delete")
    public UtilDomain remove(@RequestParam("id") String idLocker) {
        return deleteService.remove(idLocker);
    }
}
