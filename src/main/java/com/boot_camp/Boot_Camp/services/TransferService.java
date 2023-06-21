package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.BuyMenuEntity;
import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.model.wrapper.TransferPointWrapper;
import com.boot_camp.Boot_Camp.repository.BuyMenuRepository;
import com.boot_camp.Boot_Camp.repository.LockerIdRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
public class TransferService {
    @Autowired
    MemberRepository memberRepo;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    LockerIdRepository lockerIdRepository;

    @Autowired
    BuyMenuRepository buyMenuRepository;

    @Autowired
    UtilService utilService;

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
        Optional<MemberEntity> payeeOptional = memberRepo.findById(originID);

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
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return transferPointDomain;
    }

    public ValidateTransferPointDomain validateTransferPointDomain(String idLocker) {
        String idRecord = utilService.getIdRecord(idLocker);

        MemberEntity memberEntity = memberRepo.findById(idRecord).get();

        return new ValidateTransferPointDomain(memberEntity);
    }

    public HashDomain buildQrcodeForMenu(Map<String, Integer> lists,String id) {
        int amount = 0;
        ArrayList<String> menuLists = new ArrayList<>();
        String idRecord;
        Optional<StoreMenuEntity> optional;
        StoreMenuEntity entity;

        for (Map.Entry<String, Integer> list : lists.entrySet()) {
            idRecord = lockerIdRepository.findIdRecordByIdLocker(list.getKey()).getIdRecord();
            optional = storeRepository.findById(idRecord);

            if (optional.isPresent()) {
                entity = optional.get();
                if (!entity.isActive()) continue;
                amount += (entity.getExchange() * list.getValue());
                menuLists.add(list.getKey()); //id Locker
            }
        }

        BuyMenuEntity buyEntity = new BuyMenuEntity();
        buyEntity.setIdRecord(id);
        buyEntity.setIdMenu(menuLists);
        buyEntity.setAmount(amount);
        buyEntity.setScan(false);
        buyMenuRepository.save(buyEntity);

        return new HashDomain(buyEntity.getId());
    }

    public BuyMenuDomain validateMenu(String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash);
        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();
            if (entity.isScan()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Qrcode is Scan");
            entity.setScan(true);
            buyMenuRepository.save(entity);
            return new BuyMenuDomain(entity);
        } else {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Menu Not Found");
        }
    }

    public UtilDomain transferConfirmPoint(String id, String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash);
        if(optional.isPresent()) {
            BuyMenuEntity entity = optional.get();
            if (!entity.isScan()) {
                entity.setScan(true);
                buyMenuRepository.save(entity);
            }

            this.transferPoint(new TransferPointWrapper(entity.getIdRecord(),id,entity.getAmount()));
            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"hash not found");
        }
    }

    public UtilDomain cancelQrcode(String id, String hash) {
        Optional<BuyMenuEntity> optional = buyMenuRepository.findById(hash);

        if (optional.isPresent()) {
            BuyMenuEntity entity = optional.get();

            if (entity.getIdRecord().equals(id)) {
                entity.setScan(true);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bad Request");
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Qrcode not found");
    }
}
