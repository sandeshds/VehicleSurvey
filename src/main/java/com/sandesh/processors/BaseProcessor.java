package com.sandesh.processors;

import com.sandesh.model.Direction;
import com.sandesh.model.Session;
import com.sandesh.model.VehicleEntry;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseProcessor implements DataProcessor {
    @Override
    public abstract String processData(List<VehicleEntry> entries);

    protected List<VehicleEntry> getEntriesInSession(List<VehicleEntry> vehicleEntries, Session session) {
        return vehicleEntries
                .stream()
                .filter(vehicleEntry -> isWithinSession(session, vehicleEntry))
                .collect(Collectors.toList());
    }

    private boolean isWithinSession(Session session, VehicleEntry vehicleEntry) {
        return vehicleEntry.entryTime().compareTo(session.startTime) >= 0
                && vehicleEntry.entryTime().compareTo(session.endTime) < 0;
    }

    protected List<VehicleEntry> getEntriesInDirection(List<VehicleEntry> vehicleEntries, Direction direction) {
        return vehicleEntries
                .stream()
                .filter(entry -> entry.getDirection() == direction)
                .collect(Collectors.toList());
    }
}
