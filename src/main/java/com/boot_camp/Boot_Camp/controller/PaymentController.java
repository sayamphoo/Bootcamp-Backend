package com.boot_camp.Boot_Camp.controller;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.PaymentEntity;
import com.boot_camp.Boot_Camp.model.wrapper.BuyPaymentConfirmWrapper;
import com.boot_camp.Boot_Camp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

@RestController
@RequestMapping(value = "/api/v1/secure/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "/get-payment-list")
    public Iterator<PaymentEntity> getPayment() {
        return paymentService.getPayment().iterator();
    }

    @PostMapping(path = "/buy-payment-confirm")
    public UtilDomain buyPaymentConfirm(
            @RequestBody(required = true) BuyPaymentConfirmWrapper buyPaymentConfirmWrapper,
            HttpServletRequest request) {

        String id = request.getAttribute("id").toString();
        return paymentService.paymentConfirm(id, buyPaymentConfirmWrapper);
    }
}
