package com.wholesalercollector.web.controller;

import com.wholesalercollector.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.wholesalercollector.service.StockService.STOCK_FILE;
import static com.wholesalercollector.service.StockService.TEMP_DIRECTORY;
import static com.wholesalercollector.web.ResourcePath.HOME;
import static com.wholesalercollector.web.ResourcePath.PREPARE;
import static com.wholesalercollector.web.ResourcePath.UPLOAD;

@RestController
@RequestMapping(HOME)
@RequiredArgsConstructor
public class HomeController {

    private final StockService stockService;

    @GetMapping(PREPARE)
    public String prepareFile() {
        stockService.generateCsv();
        return "Download result:<br>" +
                "<form action=/file/result method=GET>" +
                "    <input type=submit value=Download>" +
                "</form><br><br>" +
                "Download unmatched SKU (if exists):<br>" +
                "<form action=/file/unmatched method=GET>" +
                "    <input type=submit value=Download>" +
                "</form>";
    }

    @SneakyThrows
    @PostMapping(UPLOAD)
    public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "Couldn't load file";
        }
        byte[] bytes = file.getBytes();
        Path path = Paths.get(TEMP_DIRECTORY + STOCK_FILE);
        File newFile = path.toFile();
        newFile.getParentFile().mkdirs();
        Files.write(path, bytes);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
        return "<p>Click to prepare your CSV file </p>" +
                "<form action=/home/prepare method=GET>" +
                "    <input type=submit value=Prepare>" +
                "</form>";
    }
}