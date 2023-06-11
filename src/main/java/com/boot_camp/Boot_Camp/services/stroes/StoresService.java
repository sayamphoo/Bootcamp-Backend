package com.boot_camp.Boot_Camp.services.stroes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
import com.boot_camp.Boot_Camp.model.domain.MenuStore;
import com.boot_camp.Boot_Camp.model.domain.MenuStoreDomain;
import com.boot_camp.Boot_Camp.model.domain.UtilStoreDomain;
import com.boot_camp.Boot_Camp.model.entity.MemberEntity;
import com.boot_camp.Boot_Camp.repository.MemberRepository;
import com.boot_camp.Boot_Camp.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot_camp.Boot_Camp.model.entity.StoreMenuEntity;
import com.boot_camp.Boot_Camp.repository.StoreRepository;
import com.boot_camp.Boot_Camp.security.Security;
import com.boot_camp.Boot_Camp.services.members.MembersService;
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
    private Security security;

    //upload menu images
    public UtilStoreDomain uploadMenu(String id, String name,
                                      int price, int exchange,
                                      int receive, List<MultipartFile> files,
                                      int category) throws Exception {

        if (files == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
        }

        String pictureUrls = "";
        for (MultipartFile file : files) {
            String fileName = String.format("%s.png", (System.currentTimeMillis()));
            Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrls = fileName;
        }
        StoreMenuEntity storeMenuEntity = new StoreMenuEntity(
                id, name, price, exchange, receive, pictureUrls, category
        );
        storeRepository.save(storeMenuEntity);

        return new UtilStoreDomain(HttpStatus.OK.value(), "Success");
    }

    //    ---------- getStores All
    public Iterable<StoreMenuEntity> getAll() {
        return storeRepository.findAll();
    }

    // getMenuDetail

    public MenuStoreDomain getStoresDetail(String id) {
        List<StoreMenuEntity> detailMenuOptional = storeRepository.findByIdAccount(id);

        if (detailMenuOptional != null && !detailMenuOptional.isEmpty()) {
            List<MenuStore> menuItems = new ArrayList<>();

            for (StoreMenuEntity storeMenuEntity : detailMenuOptional) {
                menuItems.add(new MenuStore(
                                storeMenuEntity.getId(),
                                storeMenuEntity.getNameMenu(),
                                storeMenuEntity.getPrice(),
                                storeMenuEntity.getExchange(),
                                storeMenuEntity.getReceive(),
                                storeMenuEntity.getPictures()
                        )
                );
            }

            Optional<MemberEntity> optional = memberRepository.findById(id);

            if (optional.isPresent()) {
                MemberEntity entity = optional.get();
                MenuStoreDomain menuStoreDomains = new MenuStoreDomain();
                menuStoreDomains.setId(entity.getIdAccount());
                menuStoreDomains.setStorePicture(entity.getPicture());
                menuStoreDomains.setMenuStores(menuItems);
                return menuStoreDomains;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store Not Found");
        }
    }


    public UtilStoreDomain toStore(String id) {
        Optional<MemberEntity> e = memberRepository.findById(id);
        if (e.isPresent()) {
            MemberEntity entity = e.get();
            if (!entity.isStore()) {
                entity.setStore(true);
                memberRepository.save(entity);
                return new UtilStoreDomain(HttpStatus.OK.value(), "Success");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You is already Store");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }
    }

    public String getPictureStore(String id) {
        return memberRepository.findById(id).get().getPicture();
    }


    public void deleteAll() {
        storeRepository.deleteAll();
    }

    public UtilStoreDomain uploadPictureStore(String id, MultipartFile files) throws IOException {
        if (files == null || files.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Payload is Empty or Null");
        }

        String fileName = String.format("%s.png", System.currentTimeMillis());
        Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
        Files.copy(files.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        Optional<MemberEntity> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            MemberEntity memberEntity = memberOptional.get();
            memberEntity.setPicture(fileName);
            memberRepository.save(memberEntity);
            return new UtilStoreDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }
    }


    public List<AllStoresDomain> getMenuCategory(int category) {

        List<AllStoresDomain> list = new ArrayList<>();
        List<String> idAccounts = storeRepository.findDistinctIdAccountByCategory(category);

        if (idAccounts.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "");
        Map<String, String> nameStore = memberRepository.findNameAndIdAccountByIdAccount(idAccounts);

        for (Map.Entry<String, String> entry : nameStore.entrySet()) {
            list.add(new AllStoresDomain(entry.getValue(), entry.getValue()));
        }

        return list;
    }

    public UtilStoreDomain deleteMenu(String id, String idMenu) {
        StoreMenuEntity entity = storeRepository.findByIdAndIdAccount(idMenu, id);
        if (entity != null) {
            storeRepository.delete(entity);
            return new UtilStoreDomain(HttpStatus.OK.value(), "Success");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Member Not Found");
        }

    }


}
