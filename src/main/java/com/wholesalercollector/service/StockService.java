package com.wholesalercollector.service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.wholesalercollector.adapter.ItemAdapter;
import com.wholesalercollector.domain.WholesaleItem;
import com.wholesalercollector.domain.crag.CragSport;
import com.wholesalercollector.domain.kolba.Kolba;
import com.wholesalercollector.domain.raven.Raven;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.nio.file.Files.deleteIfExists;
import static java.nio.file.Files.readAllBytes;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {

    public static final String EXTENSION_CSV = ".csv";
    public static final String EXTENSION_TXT = ".txt";
    public static final String TEMP_DIRECTORY = "src/main/resources/temp/";
    public static final String UNMATCHED_FILE_NAME = "unmatched";
    private static final String UNMATCHED_FILE = UNMATCHED_FILE_NAME + EXTENSION_TXT;
    public static final String RESULT_FILE_NAME = "result";
    public static final String STOCK_FILE_NAME = "stock";
    public static final String STOCK_FILE = STOCK_FILE_NAME + EXTENSION_CSV;

    private final RavenService ravenService;
    private final CragService cragService;
    private final KolbaService kolbaService;

    public Path generateCsv() {
        downloadFilesFromWholesales();
        return convertCsvs();
    }

    @SneakyThrows
    public Resource loadFile(String name, String extension) {
        Path path = Paths.get(TEMP_DIRECTORY).toAbsolutePath().normalize()
                .resolve(name + extension).normalize();
        return new UrlResource(path.toUri());
    }

    private void downloadFilesFromWholesales() {
        kolbaService.download();
        ravenService.download();
        cragService.download();
    }

    @SneakyThrows
    private Path convertCsvs() {
        Raven raven = (Raven) ravenService.unmarshal();
        CragSport cragSport = (CragSport) cragService.unmarshal();
        Kolba kolba = (Kolba) kolbaService.unmarshal();
        deleteIfExists(Paths.get(TEMP_DIRECTORY + RESULT_FILE_NAME + EXTENSION_CSV));

        List<ItemAdapter> itemAdapterList = raven.getItems().stream()
                .map(this::rewriteObject)
                .collect(toList());

        itemAdapterList.addAll(kolba.getItems().stream()
                .map(this::rewriteObject)
                .collect(toList()));

        itemAdapterList.addAll(cragSport.getItems().stream()
        .map(this::rewriteObject)
        .collect(toList()));

        String stock_directory = TEMP_DIRECTORY + STOCK_FILE;
        if (Paths.get(stock_directory).toFile().exists()) {
            itemAdapterList = filterBySku(itemAdapterList);
        }

        return convertToCsv(itemAdapterList);
    }

    @SneakyThrows
    private List<ItemAdapter> filterBySku(List<ItemAdapter> itemAdapter) {
        String stock = new String(readAllBytes(Paths.get(TEMP_DIRECTORY + STOCK_FILE)));
        List<String> stockList = Arrays.asList(stock.split(lineSeparator()));
        createUnmatchedResultsFile(stockList, itemAdapter.stream().map(ItemAdapter::getSKU).collect(toList()));
        return itemAdapter.stream().filter(item -> stockList.contains(item.getSKU())).collect(toList());
    }

    @SneakyThrows
    private void createUnmatchedResultsFile(List<String> stockList, List<String> itemAdapterSkuList) {
        List<String> unmatchedList = stockList.stream()
                .filter(stockItem -> !itemAdapterSkuList.contains(stockItem))
                .collect(toList());
        FileWriter writer = new FileWriter(TEMP_DIRECTORY + UNMATCHED_FILE);
        for (String str : unmatchedList) {
            writer.write(str + lineSeparator());
        }
        writer.close();
    }

    private ItemAdapter rewriteObject(WholesaleItem item) {
        return ItemAdapter.builder()
                .SKU(item.getSKU())
                .SRP(item.getSRP())
                .stawkaVat(item.getStawkaVat())
                .stanMagazynowy(item.getStanMagazynowy())
                .EAN(item.getEAN())
                .build();
    }

    @SneakyThrows
    private <T> Path convertToCsv(List<T> list) {
        Path path = Paths.get(TEMP_DIRECTORY + RESULT_FILE_NAME + EXTENSION_CSV);
        Writer writer = Files.newBufferedWriter(path, CREATE, TRUNCATE_EXISTING, WRITE);
        StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
        beanToCsv.write(list);
        writer.close();
        return path;
    }
}