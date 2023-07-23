package com.boot_camp.Boot_Camp.services;

import com.boot_camp.Boot_Camp.enums.CategoryLockerId;
import com.boot_camp.Boot_Camp.model.domain.PersonalDataDomain;
import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.HistoryTransferEntity;
import com.boot_camp.Boot_Camp.model.entity.ImeiEntity;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.model.wrapper.*;
import com.boot_camp.Boot_Camp.repository.HistoryTransferRepository;
import com.boot_camp.Boot_Camp.repository.ImeiRepository;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.repository.StatisticsMenuRepository;
import com.boot_camp.Boot_Camp.security.EmailValidator;
import com.boot_camp.Boot_Camp.security.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

@Service
public class MembersService {

    @Autowired
    private MemberRepository memberRepo;
    @Autowired
    private StatisticsMenuRepository staticRepo;
    @Autowired
    private HistoryTransferRepository historyTransferRepo;
    @Autowired
    private Security security;

    @Autowired
    private UtilService utilService;

    @Autowired
    private ImeiRepository imeiRepository;

    //-------- Sign In --------------

    public MemberDomain login(MemberWrapper w) {

        MemberDomain memberDomain = new MemberDomain();

        if (!EmailValidator.isValidEmail(w.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bad request");
        }

        MemberEntity entity = memberRepo.findByUsername(w.getUsername());
        utilService.checkActive(entity.isActive());

        if (security.comparePasswords(w.getPassword(), entity.getPassword())) {

            Map<String, Object> claims = new HashMap<>();
            String idLocker = utilService.getIdLocker(entity.getId());
            claims.put("id", idLocker);
            claims.put("isStore", entity.isStore());
            String token = security.generateToken(claims);

            memberDomain.setAccessToken(token);
            memberDomain.setAccountId(idLocker);
            memberDomain.setStore(entity.isStore());

            return memberDomain;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid login credentials.");
        }
    }

    //-------- Sign Up --------------

    public MemberDomain register(MemberWrapper w) {

        String name = w.getName();
        String birthday = w.getBirthday();
        String username = w.getUsername();
        String sex = w.getSex();
        String password = w.getPassword();
        String imei = w.getImei();

        if (name.isEmpty()
                || birthday.isEmpty()
                || username.length() < 6
                || sex.isEmpty()
                || imei.isEmpty()
                || password.length() < 8
                || !EmailValidator.isValidEmail(username)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Please provide valid input");
        }

        if (memberRepo.findByUsername(username) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This Username is already taken");
        }

        if (memberRepo.findByName(name) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This Name is already taken");
        }

        ImeiEntity imeiEntity = imeiRepository.findByImei(imei);

        MemberEntity entity = w.clone().toEntity();
        entity.setPassword(security.hashPassword(password));

        if (imeiEntity == null) {
            entity.setPoint(1000);
            imeiRepository.save(new ImeiEntity(imei));
        } else {
            entity.setPoint(0);
        }

        entity.setActive(true);
        entity.setStore(false);
        memberRepo.save(entity);
        utilService.saveLocker(CategoryLockerId.Account.getSubId(), entity.getId());
        // Login after register
        return this.login(w);
    }

    public UtilDomain resetPassword(ResetPasswordWrapper resetPasswordWrapper) {
        String id = resetPasswordWrapper.getId();
        String oldPassword = resetPasswordWrapper.getOldPassword();
        String newPassword = resetPasswordWrapper.getNewPassword();
        Optional<MemberEntity> memberOpt = memberRepo.findById(id);

        if (memberOpt.isPresent()) {
            MemberEntity entity = memberOpt.get();
            utilService.checkActive(entity.isActive());

            if (security.comparePasswords(oldPassword, entity.getPassword())) {
                entity.setPassword(security.hashPassword(newPassword));
                memberRepo.save(entity);

                return new UtilDomain(HttpStatus.OK.value(), "Success");
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Incorrect old password.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Member not found.");
        }
    }


    //    getPoint -----------------------
    public PointDomain getPoint(String id) {
        PointDomain point = new PointDomain();
        Optional<MemberEntity> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            utilService.checkActive(entity.isActive());
            point.setPoint(entity.getPoint());
            point.setId(utilService.getIdLocker(entity.getId()));
            point.setName(entity.getName());
            return point;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Member not found.");
        }

    }

    //    transferPoint

    public List<HistoryTransferDomain> getHistoryTransDomain(String id) {
        List<HistoryTransferEntity> entity = historyTransferRepo.findByAccountId(id);
        List<HistoryTransferDomain> domain = new ArrayList<>();

//        if (entity == null || entity.isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "History not found.");
//        }

        for (HistoryTransferEntity e : entity) {
            e.setOpposite(utilService.getIdLocker(e.getOpposite()));
            domain.add(new HistoryTransferDomain(e));
        }

        return domain;
    }


    public List<AllStoresDomain> getAllStores() {
        List<AllStoresDomain> domain = new ArrayList<>();
        List<MemberEntity> entity = memberRepo.findByStore(true);

        if (entity == null || entity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Store not found.");
        }

        for (MemberEntity e : entity) {
            if (!e.isActive()) continue;
            e.setId(utilService.getIdLocker(e.getId()));
            domain.add(new AllStoresDomain(e));
        }

        return domain;
    }

    public PersonalDataDomain getPersonalData(String id) {
        PersonalDataDomain domain = new PersonalDataDomain();
        Optional<MemberEntity> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            utilService.checkActive(entity.isActive());
            domain.setName(entity.getName());
            domain.setUsername(entity.getUsername());
            domain.setBirthday(entity.getBirthday().toString());
            domain.setAge(utilService.calculateAge(entity.getBirthday()));
            domain.setSex(entity.getSex());
            return domain;
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Member not found.");
        }

    }

//    --------------------------------------------

    public Iterable<MemberEntity> showDatabase() {
        return memberRepo.findAll();
    }

    public UtilDomain editPersonalData(String id, EditPersonalWrapper wrapper) {
        Optional<MemberEntity> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            utilService.checkActive(entity.isActive());
            entity.editPersonal(wrapper);
            memberRepo.save(entity);

            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }
    }

    public UtilDomain forgotPassword(ForgotPasswordWrapper wrapper) {
        LocalDate date = utilService.coverStrToLocaltime(wrapper.getBirthday());

        System.out.println(date);

        Optional<MemberEntity> optional = memberRepo.findByUsernameAndBirthday(
                wrapper.getUsername(),
                date);

        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            utilService.checkActive(entity.isActive());

            entity.setPassword(new Security().hashPassword(wrapper.getNewPassword()));
            memberRepo.save(entity);

            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "");
        }
    }

    public MemberEntity getEntityMember(String id) {
        Optional<MemberEntity> optional = memberRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not found");
        }
    }

    protected void saveMemberEntity(MemberEntity entity) {
        memberRepo.save(entity);
    }
}


































