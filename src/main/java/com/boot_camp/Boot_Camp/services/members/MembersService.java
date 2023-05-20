package com.boot_camp.Boot_Camp.services.members;

import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.MemberWrapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

//    @PostConstruct
//    public void dealete() {
//        historyTransferRepo.deleteAll();
//    }

    public MemberDomain login(MemberWrapper w, HttpServletResponse response) {
        MemberDomain memberDomain = new MemberDomain();
        if (w == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return memberDomain;
        }
        MemberEntity entity = getMember(w.getUsername());
        if (entity != null && security.comparePasswords(w.getPassword(), entity.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", entity.getIdAccount());
            claims.put("isStore", entity.isStore());
            String token = security.generateToken(claims);
            memberDomain.setCode(HttpStatus.OK.value());
            memberDomain.setAccessToken(token);
            memberDomain.setIdAccount(entity.getIdAccount());
            memberDomain.setStore(entity.isStore());
            return memberDomain;
        }

        response.setStatus(HttpStatus.NOT_FOUND.value());
        return memberDomain;
    }

    //-------- Sign Up --------------

    public MemberDomain register(MemberWrapper w, HttpServletResponse response) {
        MemberDomain memberDomain = new MemberDomain();
        String name = w.getName();
        String birthday = w.getBirthday();
        String username = w.getUsername();
        String sex = w.getSex();
        String password = w.getPassword();


        if (name.isEmpty() || birthday.isEmpty() || username.length() < 6 || sex.isEmpty() || password.length() < 8 ) {
            memberDomain.setCode(HttpStatus.BAD_REQUEST.value());
            memberDomain.setMessage("Please provide valid input");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return memberDomain;
        }

        if (getMember(username) != null) {
            memberDomain.setCode(HttpStatus.CONFLICT.value());
            response.setStatus(HttpStatus.CONFLICT.value());
            memberDomain.setMessage("This username is already taken");
            return memberDomain;
        }

        MemberWrapper e = w.clone();
        e.setIdAccount(String.valueOf(System.currentTimeMillis()));
        e.setPassword(security.encodePassword(password));
        e.setStore(true);
        e.setPoint(3000);
        memberRepo.save(e.toEntity());
        memberDomain = this.login(w, response);
        return memberDomain;
    }

    public boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void resetPassword(ResetPasswordWrapper resetPasswordWrapper) {
        String id = resetPasswordWrapper.getId();
        String oldPassword = resetPasswordWrapper.getOldPassword();
        String newPassword = resetPasswordWrapper.getNewPassword();

        Optional<MemberEntity> memberOpt = memberRepo.findById(id);
        if (memberOpt.isPresent()) {
            MemberEntity entity = memberOpt.get();
            if (security.comparePasswords(oldPassword, entity.getPassword())) {
                entity.setPassword(security.encodePassword(newPassword));
                memberRepo.save(entity);
            } else {
               // throw new IllegalArgumentException("Incorrect old password.");
            }
        } else {
           // throw new IllegalArgumentException("Member not found.");
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
        point.setCode(HttpStatus.OK.value());
        point.setPoint(entity.getPoint());
        point.setId(entity.getIdAccount());
        point.setName(entity.getName());
        return point;
    }

    //    transferPoint
    public TransferPointDomain transferPoint(TransferPointWrapper transferPointWrapper) {
        TransferPointDomain transferPointDomain = new TransferPointDomain();

        String originID = transferPointWrapper.getOriginID();
        String payeeID = transferPointWrapper.getPayee();

        System.out.println(originID + " | " + payeeID);
        int point = transferPointWrapper.getPoint();

        Optional<MemberEntity> originMemberOpt = memberRepo.findById(originID);
        MemberEntity payeeMember = memberRepo.findByIdAccount(payeeID);

        if (originMemberOpt.isPresent() && payeeMember != null) {
            MemberEntity originMember = originMemberOpt.get();

            if (originMember.getPoint() < point) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not enough point");
            }

            originMember.setPoint(originMember.getPoint() - point);
            payeeMember.setPoint(payeeMember.getPoint() + point);
            memberRepo.saveAll(Arrays.asList(originMember, payeeMember));

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String formattedDate = dateFormat.format(date);

            HistoryTransferEntity historyOrigin = new HistoryTransferEntity();
            HistoryTransferEntity historyPayee = new HistoryTransferEntity();

            historyOrigin.setIdAccount(originID);
            historyOrigin.setDate(formattedDate);
            historyOrigin.setState("withdrawal");
            historyOrigin.setPoint(-point);
            historyOrigin.setOpposite(payeeMember.getIdAccount());

            historyPayee.setIdAccount(payeeMember.getId());
            historyPayee.setDate(formattedDate);
            historyPayee.setState("deposit");
            historyPayee.setOpposite(originMember.getIdAccount());
            historyPayee.setPoint(point);

            utilService.transferSaveHistory(Arrays.asList(historyOrigin, historyPayee));

            transferPointDomain.setCode(HttpStatus.OK.value());
            transferPointDomain.setState("withdrawal");
            transferPointDomain.setPoint(point);
            transferPointDomain.setDate(formattedDate);
            transferPointDomain.setPayee(payeeID);
            transferPointDomain.setMessage(HttpStatus.OK.getReasonPhrase());
            transferPointDomain.setBalance(originMember.getPoint());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return transferPointDomain;
    }


    public List<HistoryTransferDomain> getHistoryTransDomain(String id) {
        List<HistoryTransferEntity> entity = historyTransferRepo.findByIdAccount(id);
        List<HistoryTransferDomain> domain = new ArrayList<>();

        if (entity == null || entity.isEmpty()) {
            return domain;
        }

        for (HistoryTransferEntity e : entity) {
            domain.add(new HistoryTransferDomain(e));
        }

        return domain;
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
