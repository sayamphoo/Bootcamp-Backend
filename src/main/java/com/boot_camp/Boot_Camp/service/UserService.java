package com.boot_camp.Boot_Camp.service;

import com.boot_camp.Boot_Camp.model.UserModel;
import com.boot_camp.Boot_Camp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getAll(){
        return userRepository.findAll();
    }

    public List<UserModel> delete(){
        return userRepository.findAll();
    }

}
