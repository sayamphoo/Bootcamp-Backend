package com.boot_camp.Boot_Camp.model.wrapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopsUploadMenuWrapper implements Cloneable {

    private String nameMenu;

    @Override
    public ShopsUploadMenuWrapper clone() {
        try {
            return (ShopsUploadMenuWrapper) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
