package com.example.ecommerceprototype.oms.ComuputedOverviews;

import com.example.ecommerceprototype.oms.ComputedOverviews.SalesReport;
import com.example.ecommerceprototype.oms.DB.DBManager;
import com.example.ecommerceprototype.oms.mockPIM.DiscountInformation;
import com.example.ecommerceprototype.oms.mockPIM.PlaceHolderInstGet;
import com.example.ecommerceprototype.oms.mockPIM.PriceInformation;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SalesReportTest {


    @Test
    void calcMargin() {
        assertNotNull(SalesReport.calcMargin(PlaceHolderInstGet.getInst1().getPriceInformation()));
    }

    @Test
    void calcMargin_isNotGreaterThenMax() {
        double expected = 100.0;
        if (SalesReport.calcMargin(PlaceHolderInstGet.getInst1().getPriceInformation()).doubleValue() <= expected) {
            assertTrue(true);
        }
        else {
            fail();
        }
        System.out.println(SalesReport.calcMargin(PlaceHolderInstGet.getInst1().getPriceInformation()));
    }
    @Test
    void calcMarginKR() {
        assertNotNull(SalesReport.calcMarginKR(PlaceHolderInstGet.getInst1().getPriceInformation()));
        System.out.println(SalesReport.calcMarginKR(PlaceHolderInstGet.getInst1().getPriceInformation()));
    }

    @Test
    void rev() {
        assertNotNull(SalesReport.rev(PlaceHolderInstGet.getInst1()));
        System.out.println(SalesReport.rev(PlaceHolderInstGet.getInst1()));
    }
    @Test
    void GetAmountofOrders() {
        assertNotNull(SalesReport.getAmountOfOrders("1U2U3I4D1"));
    }

    @Test
    void getQTY()
    {
        assertNotNull(SalesReport.getQTY("1U2U3I4D1").intValue());
    }


}