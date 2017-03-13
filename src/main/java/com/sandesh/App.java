package com.sandesh;

import com.sandesh.constants.Common;
import com.sandesh.io.FileReader;
import com.sandesh.model.VehicleEntry;
import com.sandesh.parser.VehicleDataParser;
import com.sandesh.processors.AverageDistanceProcessor;
import com.sandesh.processors.DataProcessor;
import com.sandesh.processors.SpeedDistributionProcessor;
import com.sandesh.processors.VehicleCountProcessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private final FileReader fileReader;
    private final Map<String, DataProcessor> dataProcessorMap;

    public App(FileReader fileReader, Map<String, DataProcessor> dataProcessorMap) {
        this.fileReader = fileReader;
        this.dataProcessorMap = dataProcessorMap;
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
            return;
        }

        dataProcessorMap.forEach( (key, processor) -> {
            System.out.println(key);
            String output = processor.processData(vehicleEntries);
            System.out.println(output);
        });
    }

    public static void main(String[] args) {
        FileReader fileReader = new FileReader();

        int[] intervalsInMinutes = {720, 60, 30, 20, 15};
        Map<String, DataProcessor> dataProcessorMap = createProcessors(intervalsInMinutes);

        new App(fileReader, dataProcessorMap).run();
    }

    private static Map<String, DataProcessor> createProcessors(int[] timeIntervals) {
        HashMap<String, DataProcessor> dataProcessorHashMap = new HashMap<>();
        for (int timeInterval : timeIntervals){
            String key = "Vehicle counts in " + timeInterval + " minute intervals";
            dataProcessorHashMap.put(key, new VehicleCountProcessor(timeInterval));
            key = "Speed distribution in " + timeInterval + " minute intervals";
            dataProcessorHashMap.put(key, new SpeedDistributionProcessor(timeInterval));
            key = "Average distance of vehicles in " + timeInterval + " minute intervals";
            dataProcessorHashMap.put(key, new AverageDistanceProcessor(timeInterval));
        }
        return dataProcessorHashMap;
    }

}
