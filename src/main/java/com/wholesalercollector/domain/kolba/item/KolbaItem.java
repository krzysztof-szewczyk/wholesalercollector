package com.wholesalercollector.domain.kolba.item;

import com.opencsv.bean.CsvIgnore;
import com.poiji.annotation.ExcelCell;
import com.wholesalercollector.domain.WholesaleItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KolbaItem implements WholesaleItem {

    @CsvIgnore
    public static final String PREFIX = "kol-";

    @ExcelCell(0)
    private String SKU;

    @ExcelCell(2)
    private String stanMagazynowy;

    @ExcelCell(3)
    private String EAN;

    private String stawkaVat;

    private String SRP;

}
