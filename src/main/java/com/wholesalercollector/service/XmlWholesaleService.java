package com.wholesalercollector.service;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.ReadableByteChannel;

import static java.lang.Long.MAX_VALUE;
import static java.nio.channels.Channels.newChannel;

public interface XmlWholesaleService extends WholesaleService {

    String EXTENSION_XML = ".xml";
    String TEMP_DIRECTORY = "src/main/resources/temp/";

    @SneakyThrows
    default void download(String url, String fileName) {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty("User-Agent", "Chrome/74.0.3729.169");
        @Cleanup InputStream inputStream = urlConnection.getInputStream();
        @Cleanup ReadableByteChannel readableByteChannel = newChannel(inputStream);
        File file = new File(TEMP_DIRECTORY + fileName + EXTENSION_XML);
        file.getParentFile().mkdirs();
        @Cleanup FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(readableByteChannel, 0, MAX_VALUE);
    }

}