package com.sandesh;

import com.sandesh.constants.Common;
import com.sandesh.io.FileReader;
import com.sandesh.model.VehicleEntry;
import com.sandesh.parser.VehicleDataParser;

import java.util.List;

public class App {
    private final FileReader fileReader;

    public App(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public void run() {
        List<String> dataLines = fileReader.readLines(ClassLoader.getSystemResource(Common.VEHICLE_DATA_FILE_PATH));

        if(dataLines.isEmpty()) {
            System.out.println("No valid data");
            return;
        }

        List<VehicleEntry> vehicleEntries = new VehicleDataParser().parseLines(dataLines);

        if(vehicleEntries.isEmpty()) {
            System.out.println("non parsable data");
        }
    }

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();
        new App(fileReader).run();
    }

}
