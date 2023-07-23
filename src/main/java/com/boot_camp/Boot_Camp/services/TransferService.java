package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.*;
import com.boot_camp.Boot_Camp.model.wrapper.BuildQrcodeForMenuWrapper;
import com.boot_camp.Boot_Camp.model.wrapper.TransferPointWrapper;
import com.boot_camp.Boot_Camp.report.exchange_and_receives.ExchangeAndReceivesEntity;
import com.boot_camp.Boot_Camp.report.exchange_and_receives.ExchangeAndReceivesService;
import com.boot_camp.Boot_Camp.repository.BuyMenuRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TransferService {
    @Autowired
    MemberRepository memberRepo;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    BuyMenuRepository buyMenuRepository;

    @Autowired
    UtilService utilService;

    @Autowired
    private ExchangeAndReceivesService exchangeAndReceivesService;

    public TransferPointDomain transferPoint(TransferPointWrapper transferPointWrapper) {
        TransferPointDomain transferPointDomain = new TransferPointDomain();

        String originID = transferPointWrapper.getOriginID();
        String payeeID = transferPointWrapper.getPayee();
        int point = transferPointWrapper.getPoint();

        if (originID.equals(payeeID)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id origin is equals payee"
            );
        }

        Optional<MemberEntity> originOptional = memberRepo.findById(originID);
        Optional<MemberEntity> payeeOptional = memberRepo.findById(payeeID);

        if (originOptional.isPresent() && payeeOptional.isPresent()) {
            MemberEntity[] member = {originOptional.get(), payeeOptional.get()};

            if (point <= 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "point incorrect");
            } else if (point > member[0].getPoint()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not enough point");
            }

            member[0].setPoint(member[0].getPoint() - point);
            member[1].setPoint(member[1].getPoint() + point);
            memberRepo.saveAll(Arrays.asList(member[0], member[1]));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String formattedDate = now.format(formatter);

            HistoryTransferEntity historyOrigin = new HistoryTransferEntity();
            HistoryTransferEntity historyPayee = new HistoryTransferEntity();

            historyOrigin.setAccountId(member[0].getId());
            historyOrigin.setDate(formattedDate);
            historyOrigin.setState("withdrawal");
            historyOrigin.setPoint(-point);
            historyOrigin.setOpposite(member[1].getId());

            historyPayee.setAccountId(member[1].getId());
            historyPayee.setDate(formattedDate);
            historyPayee.setState("deposit");
            historyPayee.setOpposite(member[0].getId());
            historyPayee.setPoint(point);

            utilService.transferSaveHistory(Arrays.asList(historyOrigin, historyPayee));

            transferPointDomain.setState("withdrawal");
            transferPointDomain.setPoint(point);
            transferPointDomain.setDate(formattedDate);
            transferPointDomain.setPayee(payeeID);
            transferPointDomain.setMessage(HttpStatus.OK.getReasonPhrase());
            transferPointDomain.setBalance(member[0].getPoint());

            return transferPointDomain;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }

    }

    public ValidateTransferPointDomain validateTransferPointDomain(String idLocker) {
        String idRecord = utilService.getIdRecord(idLocker);

        MemberEntity memberEntity = memberRepo.findById(idRecord).get();

        return new ValidateTransferPointDomain(memberEntity);
    }

    public HashDomain buildQrcodeForMenu(String id, BuildQrcodeForMenuWrapper wrapper) {
        Map<String, Integer> lists = wrapper.getMenu();
        String state = wrapper.getState();

        int amount = 0;
        ArrayList<SubBuyMenuEntity> subBuyMenuEntities = new ArrayList<>();

        String idRecord;
        Optional<StoreMenuEntity> optional;
        StoreMenuEntity entity;

        for (Map.Entry<String, Integer> list : lists.entrySet()) {
            idRecord = utilService.getIdRecord(list.getKey());
            optional = storeRepository.findById(idRecord);

            if (optional.isPresent()) {
                entity = optional.get();
                if (!entity.isActive()) continue;
                switch (state) {
                    case "RECEIVE": {
                        amount += (entity.getReceive() * list.getValue());
                        break;
                    }
                    case "EXCHANGE": {
                        amount += (entity.getExchange() * list.getValue());
                        break;
                    }
                    default: {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "State Not Found");
                    }
                }

                subBuyMenuEntities.add(new SubBuyMenuEntity(utilService.getIdRecord(list.getKey()), list.getValue()));

            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Not found menu id : %s in data", idRecord));
            }
        }

        BuyMenuEntity buyEntity = new BuyMenuEntity();
        buyEntity.setIdRecord(id);
        buyEntity.setState(state);
        buyEntity.setMenu(subBuyMenuEntities);
        buyEntity.setAmount(amount);
        buyEntity.setScan(false);
        buyEntity.setPaymentComplete(false);
        buyMenuRepository.save(buyEntity);
        return new HashDomain(buyEntity.getId());
    }

    public BuyMenuDomain validateMenu(String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash);

        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();
            if (entity.isScan()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Qrcode is Scan");

            entity.setScan(true);
            buyMenuRepository.save(entity);

            ArrayList<MenuStoreSubdomain> lists = new ArrayList<>();

            StoreMenuEntity storeMenuEntity;

            for (SubBuyMenuEntity subBuyMenuEntity : entity.getMenu()) {
                storeMenuEntity = storeRepository.findById(subBuyMenuEntity.getIdMenu()).get();
                storeMenuEntity.setId(utilService.getIdLocker(subBuyMenuEntity.getIdMenu()));
                MenuStoreSubdomain subdomain = new MenuStoreSubdomain(storeMenuEntity);
                subdomain.setAmount(subdomain.getAmount());
                lists.add(subdomain);
            }

            return new BuyMenuDomain(entity, lists);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Menu Not Found");
        }
    }

    public TransferPointDomain transferConfirmPoint(String id, String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash.trim());
        TransferPointDomain transferPointDomain;
        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();

            ArrayList<String> list = new ArrayList<>();

            ArrayList<MenuStoreSubdomain> menus = new ArrayList<>();

            for (SubBuyMenuEntity subBuyMenuEntity : entity.getMenu()) {
                StoreMenuEntity storeMenuEntity = storeRepository.findById(subBuyMenuEntity.getIdMenu()).get();
                MenuStoreSubdomain subdomain = new MenuStoreSubdomain(storeMenuEntity);
                subdomain.setAmount(subBuyMenuEntity.getAmount());
                menus.add(subdomain);
                list.add(subdomain.getNameMenu());
            }

            if (!entity.isScan()) {
                entity.setScan(true);
            }

            switch (entity.getState()) {
                case "RECEIVE": {
                    transferPointDomain = this.transferPoint(
                            new TransferPointWrapper(entity.getIdRecord(), id,
                                    entity.getAmount()));
                    transferPointDomain.setOrigin(utilService.getIdLocker(entity.getIdRecord()));
                    transferPointDomain.setPayee(utilService.getIdLocker(id));


                    exchangeAndReceivesService.saveExchangeAndReceives(
                            new ExchangeAndReceivesEntity(
                                    id,
                                    "give",
                                    entity.getAmount(),
                                    list
                            )
                    );
                    break;
                }
                case "EXCHANGE": {
                    int fee = CalculateFeeService.calculatePointFee(entity.getAmount());
                    entity.setAmount(entity.getAmount() - fee);
                    transferPointDomain = this.transferPoint(
                            new TransferPointWrapper(id, entity.getIdRecord(),
                                    entity.getAmount()));
                    transferPointDomain.setFee(fee);
                    transferPointDomain.setOrigin(utilService.getIdLocker(id));
                    transferPointDomain.setPayee(utilService.getIdLocker(entity.getIdRecord()));
                    exchangeAndReceivesService.saveExchangeAndReceives(
                            new ExchangeAndReceivesEntity(
                                    id,
                                    "exchange",
                                    entity.getAmount(),
                                    list
                            )
                    );
                    break;
                }
                default: {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "State Not Found");
                }
            }

            entity.setPaymentComplete(true);
            buyMenuRepository.save(entity);


            transferPointDomain.setState(entity.getState());
            transferPointDomain.setMenus(menus);
            return transferPointDomain;

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "hash not found");
        }
    }

    public UtilDomain cancelQrcode(String id, String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash);

        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();

            if (entity.getIdRecord().equals(id)) {
                entity.setScan(true);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Qrcode not found");
    }

    public Map<String, Boolean> TransferExchangeInteraction(String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash.trim());
        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();
            Map<String, Boolean> map = new HashMap<>();
            map.put("state", entity.isPaymentComplete());
            return map;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Hash Not found");
        }
    }
}
