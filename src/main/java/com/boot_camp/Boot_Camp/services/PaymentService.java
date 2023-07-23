package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.entity.PaymentEntity;
import com.boot_camp.Boot_Camp.model.wrapper.BuyPaymentConfirmWrapper;
import com.boot_camp.Boot_Camp.report.Sale.SaleAndBuyEntity;
import com.boot_camp.Boot_Camp.report.Sale.SaleAndBuyService;
import com.boot_camp.Boot_Camp.repository.PaymentRepository;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private MembersService membersService;

    @Autowired
    private Security security;

    @Autowired
    private SaleAndBuyService saleAndBuyService;

    @Autowired
    private UtilService utilService;

    @PostConstruct
    public void updatePayment() {
        Iterable<PaymentEntity> iterable = paymentRepo.findAll();

        if (!iterable.iterator().hasNext()) {
            ArrayList<PaymentEntity> entity = new ArrayList<>();
            entity.add(new PaymentEntity(1, 10));
            entity.add(new PaymentEntity(99, 1000));
            entity.add(new PaymentEntity(984, 10000));
            entity.add(new PaymentEntity(4899, 50000));
            paymentRepo.saveAll(entity);
        } else {
            System.out.println("\n\nHave data ! \n\n");
        }


    }

    public UtilDomain paymentConfirm(String id, BuyPaymentConfirmWrapper wrapper) { // id record

        if (wrapper.getAmount() <= 0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amount is number > 0");
        Optional<PaymentEntity> optionalPay = paymentRepo.findById(wrapper.getIdPayment());
        if (optionalPay.isPresent()) {
            MemberEntity memberEntity = membersService.getEntityMember(id);
            if (!security.comparePasswords(wrapper.getPass(), memberEntity.getPassword())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Password Not found");
            }

            if (!memberEntity.isStore() || !memberEntity.isActive()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not Active or not is Store");
            }

            PaymentEntity paymentEntity = optionalPay.get();
            memberEntity.setPoint(memberEntity.getPoint() + (paymentEntity.getPoint() * wrapper.getAmount()));
            membersService.saveMemberEntity(memberEntity);
            saleAndBuyService.saveSaleAndService(
                    new SaleAndBuyEntity(
                            utilService.getIdLocker(id),
                            (int)paymentEntity.getPrice(),
                            wrapper.getAmount(),
                            (int) (wrapper.getAmount() * paymentEntity.getPrice()),
                            true
                    ));
            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id payment not fount");
        }
    }

    public Iterable<PaymentEntity> getPayment() {
        return paymentRepo.findAll();
    }
}

















