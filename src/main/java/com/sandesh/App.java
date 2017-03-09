package com.sandesh;

import com.sandesh.constants.Common;
import com.sandesh.io.FileReader;

import java.util.List;

public class App {
    private final FileReader fileReader;

    public App(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public void run() {
        List<String> dataLines = fileReader.readLines(ClassLoader.getSystemResource(Common.VEHICLE_DATA_FILE_PATH));
    }

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        new App(fileReader).run();
    }

}
