package com.wholesalercollector.domain.crag.item;

import com.wholesalercollector.domain.WholesaleItem;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class CragSportItem implements WholesaleItem {

    public static final String PREFIX = "crag-";

    @XmlElement(name = "prod_id")
    private String SKU;

    @XmlElement(name = "taxpercent")
    private String stawkaVat;

    @XmlElement(name = "prod_amount")
    private String stanMagazynowy;

    @XmlElement(name = "prod_pkwiu")
    private String SRP;

    @XmlElement(name = "prod_ean")
    private String EAN;
}
