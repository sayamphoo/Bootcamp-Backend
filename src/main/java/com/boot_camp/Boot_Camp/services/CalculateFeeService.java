package com.boot_camp.Boot_Camp.services;

public class CalculateFeeService {
    public static int calculatePointFee(int price) {
        int fee;
        if (price >= 1 && price <= 1000) {
            fee = 20;
        } else if (price >= 1001 && price <= 5000) {
            fee = 85;
        } else if (price >= 5001 && price <= 10000) {
            fee = 150;
        } else if (price > 10000) {
            fee = 390;
        } else {
            fee = 0;
        }

        if (price < fee) {
            fee = price;
        }

        return fee;
    }
}
