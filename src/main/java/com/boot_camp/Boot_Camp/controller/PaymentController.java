package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.PaymentEntity;
import com.boot_camp.Boot_Camp.model.wrapper.BuyPaymentConfirmWrapper;
import com.boot_camp.Boot_Camp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/secure/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "get-payment-list")
    public Iterable<PaymentEntity> getPayment() {
        return paymentService.getPayment();
    }

    @PostMapping(path = "buy-payment-confirm")
    public UtilDomain buyPaymentConfirm(
            @RequestBody BuyPaymentConfirmWrapper buyPaymentConfirmWrapper,
            HttpServletRequest httpServletRequest) {

        String id = httpServletRequest.getAttribute("id").toString();
        return paymentService.paymentConfirm(id, buyPaymentConfirmWrapper);
    }
}
