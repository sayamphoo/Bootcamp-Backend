package com.boot_camp.Boot_Camp.report.exchange_and_receives;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeAndReceivesService {
    @Autowired
    private ExchangeAndReceivesRepo exchangeAndReceivesRepo;

    public boolean saveExchangeAndReceives(ExchangeAndReceivesEntity entity) {
        if (entity != null) {
            exchangeAndReceivesRepo.save(entity);
            return true;
        }

        return false;
    }
}
