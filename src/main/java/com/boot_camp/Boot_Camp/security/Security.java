package com.boot_camp.Boot_Camp.security;

import com.boot_camp.Boot_Camp.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Security {
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Security() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String encodeHash(String str) {
        if (!str.isEmpty()) {
            return bCryptPasswordEncoder.encode(str);
        } else {
            return "";
        }
    }

    public Boolean encodeHashCompare(String pass, String hash) {
        if (!(pass.isEmpty() && hash.isEmpty())) {
            return bCryptPasswordEncoder.matches(pass, hash);
        } else return false;
    }

    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    public String generateAccessToken(UserEntity userEntity) {
        Random random = new Random();
        //  String encodedString = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        Map<String, Object> map = new HashMap<>();
        map.put("id", userEntity.getId());
        map.put("position", userEntity.getPosition());

        String SECRET_KEY = Double.toHexString(Math.random() * random.nextDouble());
        System.out.println(SECRET_KEY);

        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }


}
