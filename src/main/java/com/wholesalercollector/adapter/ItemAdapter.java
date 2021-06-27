package com.wholesalercollector.adapter;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Getter;

@Builder
public class ItemAdapter {

    @CsvBindByName
    @Getter
    private String SKU;

    @CsvBindByName
    private String SRP;

    @CsvBindByName
    private String stawkaVat;

    @CsvBindByName
    private String stanMagazynowy;

    @CsvBindByName
    private String EAN;
}


