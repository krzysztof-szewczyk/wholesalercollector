package com.wholesalercollector.web;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class ResourcePath {

    public static final String FILE = "/file";
    public static final String RESULT = "/result";
    public static final String UNMATCHED = "/unmatched";
    public static final String HOME = "/home";
    public static final String PREPARE = "/prepare";
    public static final String UPLOAD = "/upload";
}
