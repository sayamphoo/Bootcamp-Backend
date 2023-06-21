package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.wrapper.*;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.boot_camp.Boot_Camp.services.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
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
            @RequestBody(required = false) MemberWrapper w) {

        return membersService.register(w);
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
    public UtilDomain resetPassword(
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


    @PutMapping("/edit-personal-data")
    public UtilDomain editPersonalData(@RequestBody EditPersonalWrapper wrapper, HttpServletRequest res) {
        String id = res.getAttribute("id").toString();
        return membersService.editPersonalData(id, wrapper);
    }

    @PutMapping("/forgot-password")
    public UtilDomain forgotPassword(
            @RequestBody ForgotPasswordWrapper wrapper) {
        return membersService.forgotPassword(wrapper);
    }

}
