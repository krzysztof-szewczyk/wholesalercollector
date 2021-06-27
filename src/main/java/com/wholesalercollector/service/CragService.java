package com.wholesalercollector.service;

import com.wholesalercollector.config.CragProperties;
import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.crag.CragSport;
import com.wholesalercollector.domain.crag.item.CragSportItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@Service
@RequiredArgsConstructor
public class CragService implements XmlWholesaleService {

    private static final String NAME_CRAG_SPORT = "crag_sport";

    private final CragProperties properties;
    private final JAXBContext JaxbContextCragSport;

    @SneakyThrows
    @Override
    public Wholesale<CragSportItem> unmarshal() {
        File file = new File(TEMP_DIRECTORY + NAME_CRAG_SPORT + EXTENSION_XML);
        Unmarshaller unmarshaller = JaxbContextCragSport.createUnmarshaller();
        CragSport cragSport = (CragSport) unmarshaller.unmarshal(file);
        cragSport.getItems().forEach(item -> {
            item.setSKU(CragSportItem.PREFIX + item.getSKU());
            item.setSRP(item.getSRP().replace(" zł", ".00"));
            item.setStanMagazynowy(substringBefore(item.getStanMagazynowy(), "."));
            fixStockByThreshold(item);
        });
        return cragSport;
    }

    @Override
    public void download() {
        download(properties.getUrl(), NAME_CRAG_SPORT);
    }

}