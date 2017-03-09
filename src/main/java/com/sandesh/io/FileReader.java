package com.sandesh.io;

import com.sandesh.constants.Common;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileReader {
    public List<String> readLines(URL filePath) {
        List<String> dataLines = new ArrayList<>();

        try {
            Files.lines(Paths.get(filePath.toURI()))
                    .forEach(dataLine -> {if(isValidLine(dataLine)) dataLines.add(dataLine); });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return dataLines;
    }

    private boolean isValidLine(String dataLine) {
        Pattern patternValidator = Pattern.compile(Common.INPUT_VALIDATION_REGEX);
        return patternValidator.matcher(dataLine).matches();
    }
}
