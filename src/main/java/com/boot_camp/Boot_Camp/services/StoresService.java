package com.boot_camp.Boot_Camp.services;

import java.io.IOException;
import java.util.*;

import com.boot_camp.Boot_Camp.enums.CategoryLockerId;
import com.boot_camp.Boot_Camp.model.domain.*;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.storages.ImageStorage;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StoresService {

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembersService membersService;

    @Autowired
    private UtilService utilService;

    @Autowired
    SaveFileService saveFileService;

    @Autowired
    private Security security;

    //upload menu images
    public UtilDomain uploadMenu(
            String id,
            String name,
            int price,
            int exchange,
            int receive,
            MultipartFile files,
            int category

    ) throws Exception {

        if (files == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }

        String RESET_IMAGE_NAME = String.valueOf(((int) System.currentTimeMillis() * (int) (Math.random() * 9.0 + 1.0)));

        String fileName = String.format("%s.png", RESET_IMAGE_NAME);

        StoreMenuEntity storeMenuEntity = new StoreMenuEntity(
                id, name, price, exchange, receive, fileName, category
        );

        storeRepository.save(storeMenuEntity);
        utilService.saveLocker(CategoryLockerId.Menu.getSubId(), storeMenuEntity.getId());
        saveFileService.saveImage(files, fileName);

        return new UtilDomain(HttpStatus.OK.value(), "Success");
    }


    // getMenuDetail

    public MenuStoreDomain getStoresDetail(String id) {

        List<StoreMenuEntity> detailMenuOptional = storeRepository.findByAccountId(id);

        if (detailMenuOptional != null) {
            List<MenuStoreSubdomain> menuItems = new ArrayList<>();

            for (StoreMenuEntity storeMenuEntity : detailMenuOptional) {
                if (!storeMenuEntity.isActive()) continue;
                storeMenuEntity.setId(utilService.getIdLocker(storeMenuEntity.getId()));
                menuItems.add(new MenuStoreSubdomain(storeMenuEntity));
            }

            Optional<MemberEntity> optional = memberRepository.findById(id);

            if (optional.isPresent()) {
                MemberEntity entity = optional.get();
                utilService.checkActive(entity.isActive());

                MenuStoreDomain menuStoreDomains = new MenuStoreDomain();
                menuStoreDomains.setId(utilService.getIdLocker(entity.getId()));
                menuStoreDomains.setStorePicture(entity.getPicture());
                menuStoreDomains.setMenuStoreSubdomains(menuItems);
                return menuStoreDomains;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Not Found");
        }
    }

    public UtilDomain toStore(String id) {
        Optional<MemberEntity> optional = memberRepository.findById(id);
        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            utilService.checkActive(entity.isActive());
            if (entity.isActive()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not activity");
            if (!entity.isStore()) {
                entity.setStore(true);
                memberRepository.save(entity);
                return new UtilDomain(HttpStatus.OK.value(), "Success");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You is already Store");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }
    }

    public UtilDomain uploadPictureStore(String id, MultipartFile files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload is Empty or Null");
        }

        String fileName = String.format("%s.png", System.currentTimeMillis());

        Optional<MemberEntity> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            MemberEntity memberEntity = memberOptional.get();
            if (memberEntity.isActive() && !memberEntity.isStore()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Store");
            utilService.checkActive(memberEntity.isActive());
            memberEntity.setPicture(fileName);
            memberRepository.save(memberEntity);
            saveFileService.saveImage(files, fileName);
            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }
    }

    public Set<AllStoresDomain> getStoreCategory(int category) {

        Set<AllStoresDomain> list = new LinkedHashSet<>();

        Set<String> uniqueIds = new HashSet<>();
        List<StoreMenuEntity> idAccounts = storeRepository.findByCategory(category);

        if (idAccounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        }

        for (StoreMenuEntity storeMenuEntity : idAccounts) {
            uniqueIds.add(storeMenuEntity.getAccountId());
        }

        MemberEntity entity;

        for (String uniqueId : uniqueIds) {

            System.out.println(uniqueId);

            entity = memberRepository.findById(uniqueId).get();

            if (!entity.isActive()) continue;

            entity.setId(utilService.getIdLocker(entity.getId()));

            AllStoresDomain newStoreDomain = new AllStoresDomain(entity);
            if (list.contains(newStoreDomain)) continue;
            list.add(newStoreDomain);
        }
        return list;
    }

    public UtilDomain updateLocation(String id, String location) {
        Optional<MemberEntity> optional = memberRepository.findById(id);

        if (optional.isPresent()) {
            MemberEntity entity = optional.get();
            if (entity.isActive() && !entity.isStore()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User Not Store");
            entity.setLocation(location);
            memberRepository.save(entity);
            return new UtilDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Member Not found");
        }
    }
}
