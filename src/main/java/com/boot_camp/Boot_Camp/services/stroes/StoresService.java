package com.boot_camp.Boot_Camp.services.stroes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import com.boot_camp.Boot_Camp.model.domain.MenuDomain;
import com.boot_camp.Boot_Camp.controller.MenuCategoryDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStore;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import com.boot_camp.Boot_Camp.storages.ImageStorage;

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
    private Security security;

    //upload menu images
    public void store(String id, String name,
                      int price, int exchange,
                      int receive, List<MultipartFile> files,
                      int category) throws Exception {

        if (files == null) {
            return;
        }

        String pictureUrls = "";
        for (MultipartFile file : files) {
            String fileName = String.format("%s.png",
                    (System.currentTimeMillis()));
            Path targetLocation = imageStorage.getImageDirectory()
                    .resolve(fileName);
            Files.copy(file.getInputStream(),
                    targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrls = fileName;
        }
        StoreMenuEntity storeMenuEntity = new StoreMenuEntity(
                id, name, price, exchange, receive, pictureUrls, category
        );
        storeRepository.save(storeMenuEntity);

    }

    //    ---------- getStores All
    public Iterable<StoreMenuEntity> getAll() {
        return storeRepository.findAll();
    }

    // getMenuDetail

    public MenuStoreDomain getStoresDetail(String id) {
        MenuStoreDomain menuStoreDomains = new MenuStoreDomain();
        List<StoreMenuEntity> detailMenuOptional = storeRepository.findByIdAccount(id);

        if (detailMenuOptional != null) {
            List<MenuStore> menuItems = new ArrayList<>();
            for (StoreMenuEntity storeMenuEntity : detailMenuOptional) {
                menuItems.add(new MenuStore(storeMenuEntity.getNameMenu(), storeMenuEntity.getPrice(), storeMenuEntity.getExchange(), storeMenuEntity.getReceive(), storeMenuEntity.getPictures()));
            }

            menuStoreDomains.setStorePicture(memberRepository.findById(id).get().getPicture());
            menuStoreDomains.setMenuStores(menuItems);

        }
        return menuStoreDomains;
    }

    public Boolean toStore(String id) {
        Optional<MemberEntity> e = memberRepository.findById(id);
        if (e.isPresent()) {
            MemberEntity entity = e.get();
            if (!entity.isStore()) {
                entity.setStore(true);
                memberRepository.save(entity);
                return true;
            }
        }
        return false;
    }

    public String getPictureStore(String id) {
        return memberRepository.findById(id).get().getPicture();
    }


    public void deleteAll() {
        storeRepository.deleteAll();
    }

    public void uploadPictureStore(String id, MultipartFile files) throws IOException {
        if (files == null || files.isEmpty()) {
            return;
        }

        String fileName = String.format("%s.png", System.currentTimeMillis());
        Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
        Files.copy(files.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Optional<MemberEntity> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            MemberEntity memberEntity = memberOptional.get();
            memberEntity.setPicture(fileName);
            memberRepository.save(memberEntity);
        } else {
            // Handle the case when member with the specified id is not found
            throw new IllegalArgumentException("Member not found with id: " + id);
        }
    }

    public List<MenuCategoryDomain> getMenuCategory(int category) {
        List<MenuCategoryDomain> domain = new ArrayList<>();
        List<StoreMenuEntity> storeMenuEntities = storeRepository.findByCategory(category);

        while (!storeMenuEntities.isEmpty()) {
            List<MenuDomain> menuDomains = new ArrayList<>();
            String accountId = storeMenuEntities.get(0).getIdAccount();

            Iterator<StoreMenuEntity> iterator = storeMenuEntities.iterator();
            while (iterator.hasNext()) {
                StoreMenuEntity entity = iterator.next();
                if (Objects.equals(accountId, entity.getIdAccount())) {
                    menuDomains.add(new MenuDomain(entity.getId(), entity.getNameMenu(), entity.getPictures()));
                    iterator.remove();
                }
            }

            domain.add(new MenuCategoryDomain(
                    accountId,
                    utilService.searchDatabaseName(accountId),
                    menuDomains));
        }

        return domain;
    }


}
