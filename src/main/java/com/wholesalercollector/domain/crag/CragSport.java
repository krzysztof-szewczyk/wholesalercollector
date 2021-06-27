package com.wholesalercollector.domain.crag;

import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.crag.item.CragSportItem;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CragSport implements Wholesale<CragSportItem> {

    @XmlElement(name="item")
    private List<CragSportItem> items;

}