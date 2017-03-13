package com.sandesh.processors;

import com.sandesh.model.VehicleEntry;

import java.util.List;

public interface DataProcessor {
    String processData(List<VehicleEntry> vehicleEntries);
}
