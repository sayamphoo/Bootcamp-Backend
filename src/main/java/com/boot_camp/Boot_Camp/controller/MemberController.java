package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.*;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/member")
public class MemberController {
    @Autowired
    private MembersService membersService;
    @Autowired
    private UtilService utilService;

    @PostMapping("/login")
    public MemberDomain login(@RequestBody(required = false) MemberWrapper w) {
        return membersService.login(w);
    }

    @PostMapping("/register")
    public MemberDomain register(
            @RequestBody(required = false) MemberWrapper w,
            HttpServletResponse response) {

        return membersService.register(w, response);
    }

    @GetMapping("/get-point")
    public PointDomain getPoint(HttpServletRequest request) {

        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User not found.");
        }
        return membersService.getPoint(id);
    }

    @GetMapping("/validate-transfer-point")
    public ValidateTransferPointDomain validateTransferPointDomain(
            @RequestParam() String id_payee) {

        return membersService.validateTransferPointDomain(id_payee);
    }

    @PutMapping("/transfer-point")
    public TransferPointDomain transferPoint(
            @RequestBody TransferPointWrapper transferPointWrapper,
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        transferPointWrapper.setOriginID(id);
        return membersService.transferPoint(transferPointWrapper);
    }

    @PutMapping("/transfer-point-for-menu")
    public UtilStoreDomain transferPointForMenu(
            @RequestParam(name = "hash") String hash,
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString();
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        return membersService.transferPointForMenu(id, hash);
    }

    @GetMapping("/get-history-transfer")
    public List<HistoryTransferDomain> getHistoryTransDomain(
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString();

        if (id == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

        return membersService.getHistoryTransDomain(id);
    }

    @PutMapping("/reset-password")
    public UtilStoreDomain resetPassword(
            @RequestBody ResetPasswordWrapper resetPasswordWrapper,
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString();
        resetPasswordWrapper.setId(id);
        return membersService.resetPassword(resetPasswordWrapper);
    }


    @GetMapping("/get-personal-data")
    public PersonalDataDomain getPersonalData(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        return membersService.getPersonalData(id);
    }

    @GetMapping("/read-hash-transfer")
    public ReadHashTransferDomain readHashTransfer(
            @RequestParam("hash") String hash) {

        return membersService.readHashTransfer(hash);
    }

    @DeleteMapping("/delete-account")
    public UtilStoreDomain deleteAccount(HttpServletRequest request) {
        String id = request.getAttribute("id").toString();
        return membersService.deleteAccount(id);
    }

    @PostMapping("/edit-personal-data")
    public UtilStoreDomain editPersonalData(@RequestBody EditPersonalWrapper wrapper, HttpServletRequest res) {
        String id = res.getAttribute("id").toString();
        return membersService.editPersonalData(id, wrapper);
    }


    @GetMapping("/show")
    public Iterable<MemberEntity> show() {

        return membersService.showDatabase();
    }

    @PutMapping("/forgot-password")
    public UtilStoreDomain forgotPassword(
            @RequestBody ForgotPasswordWrapper wrapper ) {

        System.out.println(wrapper.getBirthday());

        return membersService.forgotPassword(wrapper);

    }

}
