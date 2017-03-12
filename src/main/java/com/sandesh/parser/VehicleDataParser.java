package com.sandesh.parser;

import com.sandesh.constants.Common;
import com.sandesh.helpers.TimeParser;
import com.sandesh.model.Direction;
import com.sandesh.model.VehicleEntry;
import com.sandesh.model.VehicleEntryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VehicleDataParser {

    private static final int REQUIRED_ENTRIES_FOR_NORTH_DIRECTION = 2;
    private static final int REQUIRED_ENTRIES_FOR_SOUTH_DIRECTION = 4;
    private static final int MINIMUM_ENTRIES_NEEDED = 2;

    private int currentDay;
    private Date lastEntryTime;

    public List<VehicleEntry> parseLines(List<String> dataLines) {
        List<VehicleEntry> vehicleEntries = new ArrayList<>();
        currentDay = 0;
        lastEntryTime = new Date(0);

        while (!dataLines.isEmpty()){
            if (isInsufficientVehicleEntries(dataLines, MINIMUM_ENTRIES_NEEDED)){
                return Collections.EMPTY_LIST;
            }

            Direction directionOfVehicle = findDirectionOfVehicle(dataLines.get(0),dataLines.get(1));
            int numberOfEntriesRequired = (directionOfVehicle == Direction.SOUTH ? REQUIRED_ENTRIES_FOR_SOUTH_DIRECTION : REQUIRED_ENTRIES_FOR_NORTH_DIRECTION);
            if(isInsufficientVehicleEntries(dataLines,numberOfEntriesRequired))
                return Collections.EMPTY_LIST;
            try {
                if (directionOfVehicle == Direction.SOUTH)
                    dataLines = addSouthDirectionEntry(dataLines, vehicleEntries);
                else
                    dataLines = addNorthDirectionEntry(dataLines, vehicleEntries);
            } catch (VehicleEntryException e) {
                System.out.println("Error while creating entry");
                return Collections.EMPTY_LIST;
            }
        }
        return vehicleEntries;
    }

    private boolean isInsufficientVehicleEntries(List<String> dataLines, int numberOfEntriesRequired) {
        return dataLines.size() < numberOfEntriesRequired;
    }

    private List<String> addSouthDirectionEntry(List<String> dataLines, List<VehicleEntry> vehicleEntries) throws VehicleEntryException {
        String frontAxleSensor1Entry = dataLines.get(0);
        String frontAxleSensor2Entry = dataLines.get(1);
        String rearAxleSensor1Entry = dataLines.get(2);
        String rearAxleSensor2Entry = dataLines.get(3);
        if(!isOrderOfEntriesValid(frontAxleSensor1Entry,frontAxleSensor2Entry,rearAxleSensor1Entry,rearAxleSensor2Entry))
            throw new VehicleEntryException("Order of entries is Invalid : " + frontAxleSensor1Entry + "," + frontAxleSensor2Entry + "," + rearAxleSensor1Entry + "," + rearAxleSensor2Entry);

        int frontAxleSensor1Time = TimeParser.parseMilliSecondFromInput(frontAxleSensor1Entry);
        int frontAxleSensor2Time = TimeParser.parseMilliSecondFromInput(frontAxleSensor2Entry);
        int rearAxleSensor1Time = TimeParser.parseMilliSecondFromInput(rearAxleSensor1Entry);
        int rearAxleSensor2Time = TimeParser.parseMilliSecondFromInput(rearAxleSensor2Entry);

        int frontAxleTime = (frontAxleSensor1Time + frontAxleSensor2Time)/2;
        int rearAxleTime = (rearAxleSensor1Time + rearAxleSensor2Time)/2;

        VehicleEntry vehicleEntry = new VehicleEntry(frontAxleTime, rearAxleTime, Direction.SOUTH, currentDay);
        if (!vehicleEntry.isValidEntry())
            throw new VehicleEntryException("Not a valid entry : " + vehicleEntry);
        updateDayIfNeeded(vehicleEntry);
        vehicleEntries.add(vehicleEntry);
        return dataLines.subList(4,dataLines.size());
    }

    private boolean isOrderOfEntriesValid(String frontAxleSensor1Entry, String frontAxleSensor2Entry, String rearAxleSensor1Entry, String rearAxleSensor2Entry) {
        return frontAxleSensor1Entry.startsWith(Common.SENSOR_ONE_PREFIX)
                && frontAxleSensor2Entry.startsWith(Common.SENSOR_TWO_PREFIX)
                && rearAxleSensor1Entry.startsWith(Common.SENSOR_ONE_PREFIX)
                && rearAxleSensor2Entry.startsWith(Common.SENSOR_TWO_PREFIX);
    }

    private List<String> addNorthDirectionEntry(List<String> dataLines, List<VehicleEntry> vehicleEntries) throws VehicleEntryException {
        String frontAxleEntry = dataLines.get(0);
        String rearAxleEntry = dataLines.get(1);

        int frontAxleTime = TimeParser.parseMilliSecondFromInput(frontAxleEntry);
        int rearAxleTime = TimeParser.parseMilliSecondFromInput(rearAxleEntry);

        VehicleEntry entry = new VehicleEntry(frontAxleTime, rearAxleTime, Direction.NORTH, currentDay);
        if (!entry.isValidEntry())
            throw new VehicleEntryException("Not a valid record : " + entry);
        updateDayIfNeeded(entry);
        vehicleEntries.add(entry);
        return dataLines.subList(2,dataLines.size());
    }

    private void updateDayIfNeeded(VehicleEntry vehicleEntry) {
        Date currentTimeOfEntry = vehicleEntry.entryTime();
        if (currentTimeOfEntry.compareTo(lastEntryTime) < 0){
            currentDay++;
            vehicleEntry.setDay(currentDay);
        }
        lastEntryTime = currentTimeOfEntry;
    }

    private Direction findDirectionOfVehicle(String firstEntry, String secondEntry) {
        if (firstEntry.startsWith(Common.SENSOR_ONE_PREFIX) && secondEntry.startsWith(Common.SENSOR_ONE_PREFIX))
            return Direction.NORTH;
        return Direction.SOUTH;
    }
}
