package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.UtilDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.entity.PaymentEntity;
import com.boot_camp.Boot_Camp.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepo;

    @PostConstruct
    public void updatePayment() {
//        List<PaymentEntity> entity = new ArrayList<>();
//        entity.add(new PaymentEntity(1, 10));
//        entity.add(new PaymentEntity(99, 1_000));
//        entity.add(new PaymentEntity(984, 10_000));
//        entity.add(new PaymentEntity(4_899, 50_000));
//
//        paymentRepo.saveAll(entity);
    }

    public UtilDomain paymentConfirm(String id, String paymentID) { // id record
        Optional<PaymentEntity> optionalPay = paymentRepo.findById(paymentID);

        if (optionalPay.isPresent()) {
            MemberEntity memberEntity = ServiceDomain
                    .getMembersService()
                    .getEntityMember(id);
            PaymentEntity paymentEntity = optionalPay.get();
            memberEntity.setPoint(memberEntity.getPoint() + paymentEntity.getPoint());
            ServiceDomain.getMembersService().saveMemberEntity(memberEntity);
        }
        return new UtilDomain(HttpStatus.OK.value(), "Success");
    }

    public Iterable<PaymentEntity> getPayment() {
        return paymentRepo.findAll();
    }
}
