package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.PaymentDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.PaymentEntity;
import com.boot_camp.Boot_Camp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/v1/payment")
public class PaymentController {
//    @Autowired
//    private PaymentService paymentService;
//
//    @GetMapping(path = "get-payment-list")
//    public Iterable<PaymentEntity> getPayment() {
//        return paymentService.getPayment();
//    }
//
//    @PostMapping(path = "buy-payment-confirm")
//    public UtilDomain buyPaymentConfirm(
//            @RequestParam("id") String idPayment,
//            @RequestParam("password") String pass, HttpServletRequest httpServletRequest){
//
//        String id = httpServletRequest.getAttribute("id").toString();
//
//        return paymentService.paymentConfirm(id,pass);
//    }
}
