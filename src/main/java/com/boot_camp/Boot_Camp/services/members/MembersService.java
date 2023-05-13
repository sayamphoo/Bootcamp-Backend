package com.boot_camp.Boot_Camp.services.members;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.MemberWrapper;
import com.boot_camp.Boot_Camp.enums.StatusCodeEnum;
import com.boot_camp.Boot_Camp.model.wrapper.ResetPasswordWrapper;
import com.boot_camp.Boot_Camp.model.wrapper.TransferPointWrapper;
import com.boot_camp.Boot_Camp.repository.HistoryTransferRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.UtilService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.*;

@Service
public class MembersService {

    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private Security security;

    @Autowired
    private UtilService utilService;

    //-------- Sign In --------------
    public MemberDomain login(MemberWrapper w) {
        MemberDomain memberDomain = new MemberDomain();
        if (w == null) return memberDomain;
        MemberEntity entity = getMember(w.getUsername());
        if (security.comparePasswords(w.getPassword(), entity.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", entity.getIdMember());
            claims.put("store", entity.isStore());
            String token = security.generateToken(claims);
            memberDomain.setCode(StatusCodeEnum.SUCCESS.getValue());
            memberDomain.setAccessToken(token);
        }
        return memberDomain;
    }

    //-------- Sign Up --------------
    public MemberDomain register(MemberWrapper w) {
        MemberDomain memberDomain = new MemberDomain();
        if (getMember(w.getUsername()) == null) {
            MemberWrapper e = w.clone();
            e.setIdMember(String.format("%d", (int) System.currentTimeMillis()));
            e.setPassword(security.encodePassword(w.getPassword()));
            e.setStore(true);
            e.setPoint(0);
            memberRepo.save(e.toEntity());
            memberDomain = this.login(w);
        } else {
            memberDomain.setCode(StatusCodeEnum.NOT_ACCEPTABLE.getValue());
        }
        return memberDomain;
    }

    public void setPassword(ResetPasswordWrapper resetPasswordWrapper) {
        String id = resetPasswordWrapper.getId();
        String oldPassword = resetPasswordWrapper.getOldPassword();
        String newPassword = resetPasswordWrapper.getNewPassword();

        Optional<MemberEntity> memberOpt = memberRepo.findById(id);
        if (memberOpt.isPresent()) {
            MemberEntity entity = memberOpt.get();
            if (security.comparePasswords(oldPassword, entity.getPassword())){
                entity.setPassword(security.encodePassword(newPassword));
                memberRepo.save(entity);
            } else {
                throw new IllegalArgumentException("Incorrect old password.");
            }
        } else {
            throw new IllegalArgumentException("Member not found.");
        }
    }

    //    ตรวจสอบว่ามีสามาชิกอยู่แล้วหรือไม่ด้วย username------------------------
    private MemberEntity getMember(String username) {
        return memberRepo.findByUsername(username);
    }

    //    getPoint -----------------------
    public PointDomain getPoint(String id) {
        PointDomain point = new PointDomain();
        MemberEntity entity = memberRepo.findById(id).get();
        point.setCode(StatusCodeEnum.SUCCESS.getValue());
        point.setPoint(entity.getPoint());
        return point;
    }

    //    transferPoint
    public TransferPointDomain transferPoint(TransferPointWrapper transferPointWrapper) throws Exception {
        String originID = transferPointWrapper.getOriginID();
        String payeeID = transferPointWrapper.getPayee();
        int point = transferPointWrapper.getPoint();

        Optional<MemberEntity> originMemberOpt = memberRepo.findById(originID);
        Optional<MemberEntity> payeeMemberOpt = memberRepo.findById(payeeID);

        if (originMemberOpt.isPresent() && payeeMemberOpt.isPresent()) {
            MemberEntity originMember = originMemberOpt.get();
            MemberEntity payeeMember = payeeMemberOpt.get();

            if (originMember.getPoint() < point) {
                throw new Exception("not enough point");
            }

            originMember.setPoint(originMember.getPoint() - point);
            payeeMember.setPoint(payeeMember.getPoint() + point);
            memberRepo.saveAll(Arrays.asList(originMember, payeeMember));

            Date date = new Date();
            HistoryTransferEntity historyOrigin = new HistoryTransferEntity();
            HistoryTransferEntity historyPayee = new HistoryTransferEntity();

            historyOrigin.setMemberID(originID);
            historyOrigin.setDate(date);
            historyOrigin.setState("withdrawal");
            historyOrigin.setPoint(-point);

            historyPayee.setMemberID(payeeID);
            historyPayee.setDate(date);
            historyPayee.setState("deposit");
            historyPayee.setPoint(point);

            utilService.transferSaveHistory(Arrays.asList(historyOrigin, historyPayee));
        } else {
            throw new Exception("member not found");
        }

        return new TransferPointDomain();
    }


    public List<AllStoresDomain> getAllStores() {
        List<AllStoresDomain> domain = new ArrayList<>();
        List<MemberEntity> entity = memberRepo.findByStore(true);

        if (entity == null || entity.isEmpty()) {
            return domain;
        }

        for (MemberEntity e : entity) {
            domain.add(new AllStoresDomain(e));
        }
        return domain;
    }

    public List<HistoryTransferDomain> getHistoryTransDomain(String id) {
        List<HistoryTransferEntity> entity = historyTransferRepo.findByMemberID(id);
        List<HistoryTransferDomain> domain = new ArrayList<>();

        if (entity == null || entity.isEmpty()) {
            return domain;
        }

        for (HistoryTransferEntity e : entity) {
            domain.add(new HistoryTransferDomain(e));
        }

        return domain;
    }


//    --------------------------------------------


    public void delete() {
        memberRepo.deleteAll();
    }

    public Iterable<MemberEntity> showDatabase() {
        return memberRepo.findAll();
    }

    public BufferedImage generateQrcode(String verify) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;

        try {
            bitMatrix = qrCodeWriter.encode(
                    "test",
                    BarcodeFormat.QR_CODE,
                    250, 250);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }

        return MatrixToImageWriter.toBufferedImage(bitMatrix);

    }


}
