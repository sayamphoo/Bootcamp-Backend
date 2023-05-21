package com.boot_camp.Boot_Camp.model.domain;

public class MenuStore {
    private String nameMenu;
    private String pictures;
    private int price;
<<<<<<< HEAD
    private int exchange;
    private int receive;

    public MenuStore(String nameMenu, int price, int exchange,int receive, String pictures) {
        this.nameMenu = nameMenu;
        this.pictures = pictures;
        this.price = price;
        this.exchange = exchange;
        this.receive = receive;
    }

    public int getReceive() {
        return receive;
    }

    public void setReceive(int receive) {
        this.receive = receive;
=======
    private int point;

    public MenuStore(String nameMenu, int price, int point, String pictures) {
        this.nameMenu = nameMenu;
        this.pictures = pictures;
        this.price = price;
        this.point = point;
>>>>>>> origin/master
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

<<<<<<< HEAD
    public int getExchange() {
        return exchange;
    }

    public void setExchange(int exchange) {
        this.exchange = exchange;
=======
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
>>>>>>> origin/master
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
