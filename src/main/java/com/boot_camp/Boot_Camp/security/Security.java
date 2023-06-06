package com.boot_camp.Boot_Camp.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Security extends JwtTokenProvider {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Security() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String str) {
        if (str != null) {
            return bCryptPasswordEncoder.encode(str);
        }
        return "";
    }

    public Boolean comparePasswords(String pass, String hash) {
        if (!(pass.isEmpty() && hash.isEmpty())) {
            return bCryptPasswordEncoder.matches(pass, hash);
        } else return false;
    }

//    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

}
