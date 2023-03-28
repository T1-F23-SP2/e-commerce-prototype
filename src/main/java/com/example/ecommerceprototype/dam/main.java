package com.example.ecommerceprototype.dam;

import java.util.Date;

public class main {
    public static void main(String[] args) {
        DAM test = new DAM();

        DigitalAsset newAsset = new DigitalAsset(
                "name",
                "path",
                "type",
                293,
                "TT12345678",
                new Date()
        );

        DigitalAsset newAsset2 = new DigitalAsset(
                "name2",
                "path",
                "type",
                293,
                "TT12345678",
                new Date()
        );

        if (test.addDigitalAsset(newAsset)) {
            System.out.println("asset added");
        } else {
            System.out.println("already exists in manager or database");
            System.out.println(newAsset.getId());
        }

    }
}
