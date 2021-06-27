package com.wholesalercollector.web.controller;

import com.wholesalercollector.service.StockService;
import com.wholesalercollector.web.ResourcePath;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.wholesalercollector.service.StockService.EXTENSION_CSV;
import static com.wholesalercollector.service.StockService.EXTENSION_TXT;
import static com.wholesalercollector.service.StockService.RESULT_FILE_NAME;
import static com.wholesalercollector.service.StockService.UNMATCHED_FILE_NAME;

@RestController
@RequestMapping(ResourcePath.FILE)
@RequiredArgsConstructor
public class FileController {

    private final static MediaType TEXT_CSV_TYPE = new MediaType("text", "csv");
    private final static MediaType TEXT_TXT_TYPE = new MediaType("text", "txt");

    private final StockService stockService;

    @GetMapping(ResourcePath.RESULT)
    public ResponseEntity<?> downloadResult() {
        Resource resource = stockService.loadFile(RESULT_FILE_NAME, EXTENSION_CSV);
        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.ok()
                .contentType(TEXT_CSV_TYPE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(ResourcePath.UNMATCHED)
    public ResponseEntity<?> downloadUnmatched() {
        Resource resource = stockService.loadFile(UNMATCHED_FILE_NAME, EXTENSION_TXT);
        return ResponseEntity.ok()
                .contentType(TEXT_TXT_TYPE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}