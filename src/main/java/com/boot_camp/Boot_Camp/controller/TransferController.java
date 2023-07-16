package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.wrapper.BuildQrcodeForMenuWrapper;
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
            @RequestBody BuildQrcodeForMenuWrapper wrapper, HttpServletRequest req) {
        String id = req.getAttribute("id").toString();
        System.out.println(id);
        return transferService.buildQrcodeForMenu(id,wrapper);
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
    public TransferPointDomain transferConfirmPoint(
            @RequestBody() Map<String,String> map,
            HttpServletRequest req) {

        String hash = map.get("hash");

        String id = req.getAttribute("id").toString();

        return transferService.transferConfirmPoint(id, hash);
    }

    @GetMapping(path = "/transfer-exchange-interaction")
    public Map<String,Boolean> TransferExchangeInteraction(@RequestParam String hash){
        return transferService.TransferExchangeInteraction(hash);
    }
}
