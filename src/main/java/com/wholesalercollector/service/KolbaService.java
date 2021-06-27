package com.wholesalercollector.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.wholesalercollector.config.KolbaProperties;
import com.wholesalercollector.domain.Wholesale;
import com.wholesalercollector.domain.kolba.Kolba;
import com.wholesalercollector.domain.kolba.item.KolbaItem;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

import static com.poiji.bind.Poiji.fromExcel;
import static java.lang.Long.MAX_VALUE;
import static java.nio.channels.Channels.newChannel;
import static org.apache.commons.lang3.StringUtils.substringBefore;

@Slf4j
@Service
@RequiredArgsConstructor
public class KolbaService implements WholesaleService {

    public static final String EXTENSION_XLSX = ".xlsx";
    public static final String TEMP_DIRECTORY = "src/main/resources/temp/";
    private static final String NAME_KOLBA = "kolba";

    private final KolbaProperties properties;

    @SneakyThrows
    @Override
    public Wholesale<KolbaItem> unmarshal() {
        File file = new File(TEMP_DIRECTORY + NAME_KOLBA + EXTENSION_XLSX);
        Wholesale<KolbaItem> kolba = new Kolba(fromExcel(file, KolbaItem.class));
        file.delete();
        kolba.getItems().forEach(item -> {
            item.setSKU(KolbaItem.PREFIX + item.getSKU());
            item.setStanMagazynowy(substringBefore(item.getStanMagazynowy(), "."));
            fixStockByThreshold(item);
        });
        return kolba;
    }

    @Override
    @SneakyThrows
    public void download() {
        @Cleanup WebClient webClient = new WebClient();
        HtmlPage loginPage = getLoginPage(webClient);
        loginPage.getForms().stream()
                .findFirst()
                .ifPresent(this::logIn);
        @Cleanup InputStream contentAsStream = getInputStream(webClient);
        @Cleanup ReadableByteChannel readableByteChannel = newChannel(contentAsStream);
        File file = new File(TEMP_DIRECTORY + NAME_KOLBA + EXTENSION_XLSX);
        file.getParentFile().mkdirs();
        @Cleanup FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(readableByteChannel, 0, MAX_VALUE);
    }

    private InputStream getInputStream(WebClient webClient) {
        InputStream contentAsStream = null;
        try {
            contentAsStream = webClient.getPage("https://b2b.kolba.pl/pl/download/products").getWebResponse().getContentAsStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentAsStream;
    }

    private void logIn(HtmlForm loginForm) {
        loginForm.getButtonsByName("").stream()
                .findFirst()
                .ifPresent(loginButton -> {
                    fillPasses(loginForm);
                    performLogIn(loginButton);
                });
    }

    private void performLogIn(HtmlButton loginButton) {
        try {
            loginButton.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillPasses(HtmlForm loginForm) {
        HtmlTextInput textField = loginForm.getInputByName("_username");
        textField.setValueAttribute(properties.getEmail());
        HtmlPasswordInput textField2 = loginForm.getInputByName("_password");
        textField2.setValueAttribute(properties.getPassword());
    }

    private HtmlPage getLoginPage(WebClient webClient) {
        HtmlPage loginPage = null;
        try {
            loginPage = webClient.getPage("https://b2b.kolba.pl/pl/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginPage;
    }

}