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
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private static MembersService membersService;

    @PostConstruct
    public void updatePayment() {
        try {
            Iterable<PaymentEntity> iterable = paymentRepo.findAll();

            if (!iterable.iterator().hasNext()) {
                ArrayList<PaymentEntity> entity = new ArrayList<>();
                entity.add(new PaymentEntity(1, 10));
                entity.add(new PaymentEntity(99, 1000));
                entity.add(new PaymentEntity(984, 10000));
                entity.add(new PaymentEntity(4899, 50000));
                paymentRepo.saveAll(entity);
            }
        } catch (Exception e) {
            System.out.println("\n\n\n\n\n\n\nHave data ! \n\n\n\n\n\n");
        }


    }

    public UtilDomain paymentConfirm(String id, String paymentID) { // id record
        Optional<PaymentEntity> optionalPay = paymentRepo.findById(paymentID);

        if (optionalPay.isPresent()) {
            MemberEntity memberEntity = membersService.getEntityMember(id);
            PaymentEntity paymentEntity = optionalPay.get();
            memberEntity.setPoint(memberEntity.getPoint() + paymentEntity.getPoint());
            membersService.saveMemberEntity(memberEntity);
        }
        return new UtilDomain(HttpStatus.OK.value(), "Success");
    }

    public Iterable<PaymentEntity> getPayment() {
        return paymentRepo.findAll();
    }
}
