package com.boot_camp.Boot_Camp.report.Sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleAndBuyService {
    @Autowired
    private SaleAndBuyRepo saleAndBuyRepo;

    public boolean saveSaleAndService(SaleAndBuyEntity saleAndBuyEntity) {
        if (saleAndBuyEntity != null) {
            saleAndBuyRepo.save(saleAndBuyEntity);
            return true;
        }

        return false;
    }
}
