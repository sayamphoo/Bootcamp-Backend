package com.boot_camp.Boot_Camp.services.stroes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.boot_camp.Boot_Camp.model.domain.MenuStore;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.ShopsRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.members.MembersService;
import com.boot_camp.Boot_Camp.storages.ImageStorage;


@Service
public class StoresService {

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private ShopsRepository shopsRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MembersService membersService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private Security security;

    //upload menu images
    public void store(String id, String name, int price, int point, List<MultipartFile> files) throws Exception {
        if (files == null) {
            return;
        }

        String pictureUrls = "";
        for (MultipartFile file : files) {
            String fileName = String.format("%s.png", (System.currentTimeMillis()));
            Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrls = fileName;
        }
        StoreMenuEntity storeMenuEntity = new StoreMenuEntity(id, name, price, point, pictureUrls);
        shopsRepository.save(storeMenuEntity);

    }

    //    ---------- getStores All
    public Iterable<StoreMenuEntity> getAll() {
        return shopsRepository.findAll();
    }

    // getMenuDetail

    public MenuStoreDomain getStoresDetail(String id) {
        MenuStoreDomain menuStoreDomains = new MenuStoreDomain();
        List<StoreMenuEntity> detailMenuOptional = shopsRepository.findByIdAccount(id);

        if (detailMenuOptional != null) {
            List<MenuStore> menuItems = new ArrayList<>();
            for (StoreMenuEntity storeMenuEntity : detailMenuOptional) {
                menuItems.add(new MenuStore(storeMenuEntity.getNameMenu(), storeMenuEntity.getPrice(), storeMenuEntity.getPoint(), storeMenuEntity.getPictures()));
            }

            menuStoreDomains.setStorePicture(memberRepository.findById(id).get().getPicture());
            menuStoreDomains.setMenuStores(menuItems);

        }

        return menuStoreDomains;
    }

    public String getPictureStore(String id) {
        return memberRepository.findById(id).get().getPicture();
    }


    public void uploadPictureStore(String id, MultipartFile files) throws IOException {
        if (files == null) {
            return;
        }
        String fileName = String.format("%s.png", (System.currentTimeMillis()));
        Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
        Files.copy(files.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        MemberEntity e = memberRepository.findById(id).get();
        e.setPicture(fileName);
        memberRepository.save(e);
    }

    public void deleteAll() {
        shopsRepository.deleteAll();
    }
}
