package com.boot_camp.Boot_Camp.services.stroes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import com.boot_camp.Boot_Camp.model.domain.AllStoresDomain;
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
    private MembersService membersService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private Security security;

    public void store(String token, String name, List<MultipartFile> files) throws Exception {
        if (files == null || !security.validateToken(token)) {
            return;
        }

        List<String> pictureUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = String.format("%s.png", (System.currentTimeMillis()));
            Path targetLocation = imageStorage.getImageDirectory().resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            pictureUrls.add(fileName);
        }

        String memberId = utilService.searchDatabaseID(security.getIdToken(token));
        StoreMenuEntity storeMenuEntity = new StoreMenuEntity(memberId, name, pictureUrls);
        shopsRepository.save(storeMenuEntity);
    }

//    ---------- getStores All

    public Iterable<StoreMenuEntity> getAll() {
        return shopsRepository.findAll();
    }

    public void deleteAll() {
        shopsRepository.deleteAll();
    }
}
