package com.boot_camp.Boot_Camp.model.domain;

public class MenuStore {
    private String nameMenu;
    private String pictures;
    private int price;
    private int point;

    public MenuStore(String nameMenu, int price, int point, String pictures) {
        this.nameMenu = nameMenu;
        this.pictures = pictures;
        this.price = price;
        this.point = point;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getNameMenu() {
        return nameMenu;
    }

    public void setNameMenu(String nameMenu) {
        this.nameMenu = nameMenu;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }
}
