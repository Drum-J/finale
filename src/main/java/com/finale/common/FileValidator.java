package com.finale.common;

import java.util.Arrays;
import java.util.List;

public class FileValidator {

    private static final List<String> EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    public static boolean isImage(String fileName) {
        String extension = getFileExtension(fileName);

        return EXTENSIONS.contains(extension.toLowerCase());
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
