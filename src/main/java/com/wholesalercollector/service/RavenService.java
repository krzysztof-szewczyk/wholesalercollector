package com.wholesalercollector.service;

import com.wholesalercollector.config.RavenProperties;
import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.raven.Raven;
import com.wholesalercollector.domain.raven.item.RavenItem;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@Service
@RequiredArgsConstructor
public class RavenService implements XmlWholesaleService {

    private static final String NAME_RAVEN = "raven";

    private final RavenProperties properties;
    private final JAXBContext JaxbContextRaven;

    @SneakyThrows
    @Override
    public Wholesale<RavenItem> unmarshal() {
        File file = new File(TEMP_DIRECTORY + NAME_RAVEN + EXTENSION_XML);
        Unmarshaller unmarshaller = JaxbContextRaven.createUnmarshaller();
        Raven raven = (Raven) unmarshaller.unmarshal(file);
        file.delete();
        raven.getItems().forEach(item -> {
            item.setSKU(RavenItem.PREFIX + item.getSKU());
            String srp = item.getSRP();
            if (isNotBlank(srp)) {
                item.setSRP(srp.replace(",", "."));
            }
            item.setStawkaVat(substringBefore(item.getStawkaVat(), ","));
            fixStockByThreshold(item);
        });
        return raven;
    }

    @Override
    public void download() {
        download(properties.getUrl(), NAME_RAVEN);
    }
}