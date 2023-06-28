package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.wrapper.TransferPointWrapper;
import com.boot_camp.Boot_Camp.repository.BuyMenuRepository;
import com.boot_camp.Boot_Camp.services.TransferService;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/secure/transfer")
public class TransferController {
    @Autowired
    TransferService transferService;

    @Autowired
    BuyMenuRepository buyMenuRepository;

    @Autowired
    UtilService utilService;

    @GetMapping("/validate-transfer-point")
    public ValidateTransferPointDomain validateTransferPointDomain(
            @RequestParam(name = "idPayee") String idPayee) {

        return transferService.validateTransferPointDomain(idPayee);
    }

    @PutMapping("/transfer-point")
    public TransferPointDomain transferPoint(
            @RequestBody TransferPointWrapper transferPointWrapper,
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString( );
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        transferPointWrapper.setOriginID(id);
        transferPointWrapper.setPayee(utilService.getIdRecord(transferPointWrapper.getPayee()));
        return transferService.transferPoint(transferPointWrapper);
    }

    @PostMapping("/build-qrcode-for-menu")
    public HashDomain buildQrcodeForMenu(
            @RequestBody Map<String, Integer> lists, HttpServletRequest req) {

        String id = req.getAttribute("id").toString();

        return transferService.buildQrcodeForMenu(lists, id);
    }

    @DeleteMapping("/cancel-qrcode")
    public UtilDomain cancelQrcode(String hash, HttpServletRequest req) {

        String id = req.getAttribute("id").toString();
        return transferService.cancelQrcode(id, hash);
    }


    @GetMapping("/validate-menu-for-menu")
    private BuyMenuDomain validateMenu(@RequestParam(name = "hash") String hash) {

        return transferService.validateMenu(hash);
    }

    @PutMapping("/transfer-confirm-point")
    public UtilDomain transferConfirmPoint(@RequestParam(name = "hash") String hash, HttpServletRequest req) {

        String id = req.getAttribute("name").toString();
        return transferService.transferConfirmPoint(id, hash);
    }

}
