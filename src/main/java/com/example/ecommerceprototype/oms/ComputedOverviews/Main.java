package com.example.ecommerceprototype.oms.ComputedOverviews;

import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.pim.exceptions.CategoryNotFoundException;
import com.example.ecommerceprototype.pim.exceptions.NotFoundException;
import com.example.ecommerceprototype.pim.exceptions.UUIDNotFoundException;
import com.example.ecommerceprototype.pim.product_information.PIMDriver;
import com.example.ecommerceprototype.pim.product_information.ProductInformation;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        List<ProductInformation> productInformationList;
        PIMDriver pimDriver = new PIMDriver();

        try {
            productInformationList = pimDriver.getAllProducts();
        } catch (UUIDNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println(productInformationList.size());



    }
}