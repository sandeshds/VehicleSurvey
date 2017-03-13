package com.sandesh.processors;

import com.sandesh.constants.Common;
import com.sandesh.constants.TimeConstants;
import com.sandesh.model.Direction;
import com.sandesh.model.Session;
import com.sandesh.model.VehicleEntry;

import java.util.List;

public class AverageDistanceProcessor extends BaseProcessor {
    private final int sessionInterval;

    public AverageDistanceProcessor(int sessionInterval) {
        this.sessionInterval = sessionInterval;
    }

    @Override
    public String processData(List<VehicleEntry> entries) {
        StringBuilder sb = new StringBuilder();

        List<Session> sessions = Session.createWithInterval(sessionInterval);
        for (Session session : sessions){
            List<VehicleEntry> entriesInTheSession = getEntriesInSession(entries, session);
            List<VehicleEntry> entriesInSouthDirection = getEntriesInDirection(entriesInTheSession, Direction.SOUTH);
            List<VehicleEntry> entriesInNorthDirection = getEntriesInDirection(entriesInTheSession, Direction.NORTH);
            double distanceInSouthDirection = getAverageDistanceBetweenCars(entriesInSouthDirection);
            double distanceInNorthDirection = getAverageDistanceBetweenCars(entriesInNorthDirection);
            sb.append(session).append(" Average distance between the cars travelling in southern direction = ").append(distanceInSouthDirection).append(" meters, in northern direction = ").append(distanceInNorthDirection).append(" meters\n");
        }
        return sb.toString();
    }

    private double getAverageDistanceBetweenCars(List<VehicleEntry> entriesInTheSession) {
        if (entriesInTheSession.isEmpty())
            return 0;
            long timeOfLastVehicle = entriesInTheSession.get(entriesInTheSession.size() - 1).entryTime().getTime();
            long timeOfFirstVehicle = entriesInTheSession.get(0).entryTime().getTime();
            double averageTimeGapBetweenVehicles = ((double) timeOfLastVehicle - (double) timeOfFirstVehicle) / (double)entriesInTheSession.size();
            return averageTimeGapBetweenVehicles * Common.AVERAGE_SPEED/ TimeConstants.MILLISECONDS_IN_A_SECOND;
    }
}
