package com.wholesalercollector.domain.kolba;

import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.kolba.item.KolbaItem;
import lombok.Value;

import java.util.List;

@Value
public class Kolba implements Wholesale<KolbaItem> {

    List<KolbaItem> items;

}