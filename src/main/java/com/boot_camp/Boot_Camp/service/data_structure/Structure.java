package com.boot_camp.Boot_Camp.service.data_structure;

import java.util.HashMap;
import java.util.Map;

public class Structure {
    public  Map<String,Object> resUserClient(String id, Object obj) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("position",obj);
        return claims;
    }
}
