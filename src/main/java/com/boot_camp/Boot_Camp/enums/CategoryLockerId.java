package com.boot_camp.Boot_Camp.enums;



public enum CategoryLockerId {

    Account("1"),
    Menu("2");

    private final String sub;

    CategoryLockerId(String id) {
        this.sub = id;
    }

    public String getSubId(){
        return sub;
    }
}
