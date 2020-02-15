package com.maple.utils;

import com.maple.utils.exceptions.NoFileExtensionException;

import java.io.File;

public class FileUtils {
    private static final int SUBSTRING_NOT_FOUND = -1;

    public static String getExtension(File file) throws NoFileExtensionException {
        String fileName = file.getName();

        int dotIndex = fileName.indexOf('.');
        if (dotIndex == SUBSTRING_NOT_FOUND) {
            throw new NoFileExtensionException();
        }

        return fileName.substring(dotIndex + 1);
    }
}
