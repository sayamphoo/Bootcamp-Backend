package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.HistoryTransferDomain;
import com.boot_camp.Boot_Camp.model.domain.TransferPointDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.domain.MemberDomain;
import com.boot_camp.Boot_Camp.model.domain.PointDomain;
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
    public MemberDomain signIn(@RequestBody(required = false) MemberWrapper w,HttpServletResponse response) {
        return membersService.login(w,response);
    }

    @PostMapping("/register")
    public MemberDomain signUp(@RequestBody(required = false) MemberWrapper w, HttpServletResponse response) {
        return membersService.register(w,response);
    }

    @GetMapping("/get-point")
    public PointDomain getPoint(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return membersService.getPoint(id);
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
    public void resetPassword(@RequestBody ResetPasswordWrapper resetPasswordWrapper, HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        resetPasswordWrapper.setId(id);
        membersService.resetPassword(resetPasswordWrapper);
    }

//    ----------------------------------------------

    @GetMapping("/delete")
    public void delete() {
        membersService.delete();
    }

    @GetMapping("/show")
    public Iterable<MemberEntity> show() {
        return membersService.showDatabase();
    }

    @GetMapping(path = "generate-qr", produces = MediaType.IMAGE_PNG_VALUE)
    public BufferedImage generateQrcode(HttpServletRequest request) {
        return membersService.generateQrcode(request.getHeader("verify"));
    }

}
