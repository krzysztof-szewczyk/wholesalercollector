package com.wholesalercollector.domain;

public interface WholesaleItem {

    String getSKU();

    String getSRP();

    String getStawkaVat();

    String getStanMagazynowy();

    String getEAN();

    void setSKU(String string);

    void setSRP(String string);

    void setStawkaVat(String string);

    void setStanMagazynowy(String string);

    void setEAN(String string);
}
