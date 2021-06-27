package com.wholesalercollector.domain;

import java.util.List;

public interface Wholesale<T extends WholesaleItem> {

    List<T> getItems();
}

