package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.MemberWrapper;
import com.boot_camp.Boot_Camp.model.wrapper.ResetPasswordWrapper;
import com.boot_camp.Boot_Camp.model.wrapper.TransferPointWrapper;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/member")
public class MemberController {
    @Autowired
    private MembersService membersService;
    @Autowired
    private UtilService utilService;

    @PostMapping("/login")
    public MemberDomain login(@RequestBody(required = false) MemberWrapper w, HttpServletResponse response) {
        return membersService.login(w, response);
    }

    @PostMapping("/register")
    public MemberDomain register(@RequestBody(required = false) MemberWrapper w, HttpServletResponse response) {
        return membersService.register(w, response);
    }

    @GetMapping("/get-point")
    public PointDomain getPoint(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return membersService.getPoint(id);
    }

    @GetMapping("/validate-transfer-point")
    public ValidateTransferPointDomain validateTransferPointDomain(@RequestParam() String id_payee) {
        return membersService.validateTransferPointDomain(id_payee);
    }

    @PutMapping("/transfer-point")
    public TransferPointDomain transferPoint(@RequestBody TransferPointWrapper transferPointWrapper, HttpServletRequest request) throws Exception {
        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        transferPointWrapper.setOriginID(id);
        return membersService.transferPoint(transferPointWrapper);
    }

    @GetMapping("/get-history-transfer")
    public List<HistoryTransferDomain> getHistoryTransDomain(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        return membersService.getHistoryTransDomain(id);
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestBody ResetPasswordWrapper resetPasswordWrapper, HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        resetPasswordWrapper.setId(id);
        return membersService.resetPassword(resetPasswordWrapper);
    }

    //dsdssdsd

    @GetMapping("/get-personal-data")
    public PersonalDataDomain getPersonalData(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        return membersService.getPersonalData(id);
    }

//    @PostMapping("/validate-user")
//    public ValidateDomain validateDomain() {
//        return membersService.validate();
//    }
//    ----------------------------------------------

    @GetMapping("/delete")
    public void delete() {
        membersService.delete();
    }

    @GetMapping("/show")
    public Iterable<MemberEntity> show() {
        return membersService.showDatabase();
    }

//    @GetMapping(path = "generate-qr", produces = MediaType.IMAGE_PNG_VALUE)
//    public BufferedImage generateQrcode(HttpServletRequest request) {
//        return membersService.generateQrcode(request.getHeader("verify"));
//    }

}
