package com.wholesalercollector.service;

import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.WholesaleItem;

import static java.lang.Long.parseLong;

public interface WholesaleService {

    Long THRESHOLD = 2L;

    void download();

    Wholesale<? extends WholesaleItem> unmarshal();

    default void fixStockByThreshold(WholesaleItem item) {
        try {
            if (parseLong(item.getStanMagazynowy()) < THRESHOLD) {
                item.setStanMagazynowy("0");
            }
        } catch (NumberFormatException e) {
            System.out.println("Cannot convert stock string value to number. The value is: " + item.getStanMagazynowy());
        }
    }

}