package com.wholesalercollector.domain.raven.item;

import com.opencsv.bean.CsvIgnore;
import com.wholesalercollector.domain.WholesaleItem;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.oxm.annotations.XmlPath;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class RavenItem implements WholesaleItem {

    @CsvIgnore
    public static final String PREFIX = "rav-";

    @XmlPath("Codes/Code[@type='4']/text()")
    private String SKU;

    @XmlPath("Prices/Price_ZL/text()")
    private String SRP;

    @XmlPath("Prices/TaxRateValue/text()")
    private String stawkaVat;

    @XmlPath("Stock/Quantity/text()")
    private String stanMagazynowy;

    @XmlPath("Codes/Code[@type='1']/text()")
    private String EAN;
}
