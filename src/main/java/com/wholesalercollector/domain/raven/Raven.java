package com.wholesalercollector.domain.raven;

import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.raven.item.RavenItem;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@XmlRootElement(name = "Supplier-Catalog")
@XmlAccessorType(FIELD)
@Data
public class Raven implements Wholesale<RavenItem> {

    @XmlElementWrapper(name = "Products")
    @XmlElement(name = "Product")
    private List<RavenItem> items;
}