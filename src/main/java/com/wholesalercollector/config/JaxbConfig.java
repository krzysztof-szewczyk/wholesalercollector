package com.wholesalercollector.config;

import com.wholesalercollector.domain.crag.CragSport;
import com.wholesalercollector.domain.raven.Raven;
import lombok.SneakyThrows;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.bind.JAXBContext;

@Configuration
public class JaxbConfig {

    @SneakyThrows
    @Bean
    public JAXBContext JaxbContextRaven() {
        return JAXBContextFactory.createContext(new Class[]{Raven.class, ObjectFactory.class}, null);
    }

    @SneakyThrows
    @Bean
    public JAXBContext JaxbContextCragSport() {
        return JAXBContextFactory.createContext(new Class[]{CragSport.class, ObjectFactory.class}, null);
    }
}
