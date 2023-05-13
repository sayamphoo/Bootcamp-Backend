package com.boot_camp.Boot_Camp.enums;

public enum StatusCodeEnum {
    SUCCESS("Success",200),
    NOT_FOUND("Not Found",404),
    FORBIDDEN("Forbidden",403),
    NOT_ACCEPTABLE("Not Acceptable",406);

    private final String key;
    private final int value;

    StatusCodeEnum(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}
