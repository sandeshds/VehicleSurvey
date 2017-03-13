package com.sandesh.processors;


import com.sandesh.constants.Common;
import com.sandesh.model.Direction;
import com.sandesh.model.Session;
import com.sandesh.model.VehicleEntry;

import java.util.List;
import java.util.stream.Collectors;

public class VehicleCountProcessor extends BaseProcessor {
    private final int intervalInMinutes;

    public VehicleCountProcessor(int intervalInMinutes) {
        this.intervalInMinutes = intervalInMinutes;
    }

    @Override
    public String processData(List<VehicleEntry> vehicleEntries) {
        Session peakSession = null;
        int peakVehicleCount = 0;
        StringBuilder stringBuilder = new StringBuilder();

        List<Session> sessions = Session.createWithInterval(intervalInMinutes);
        if (sessions.isEmpty())
            return "";
        for(Session session : sessions){
            int totalCountAcrossDays = 0;
            List<VehicleEntry> sessionEntries = getEntriesInSession(vehicleEntries, session);
            stringBuilder.append(session.toString());
            for (int day = 0; day < Common.NUMBER_OF_DAYS; day++) {
                int southCount = countEntriesForAGivenDayInADirection(sessionEntries, day, Direction.SOUTH);
                int northCount = countEntriesForAGivenDayInADirection(sessionEntries, day, Direction.NORTH);
                stringBuilder.append(" || Day ").append(day).append(" Count South = ").append(southCount).append(" North = ").append(northCount);
                totalCountAcrossDays += southCount + northCount;
            }
            stringBuilder.append('\n');
            if (totalCountAcrossDays > peakVehicleCount){
                peakVehicleCount = totalCountAcrossDays;
                peakSession = session;
            }
        }
        stringBuilder.append('\n').append(peakSession).append(" is peak session with a vehicle count of ").append(peakVehicleCount).append(" across ").append(Common.NUMBER_OF_DAYS).append(" days");
        return stringBuilder.toString();
    }

    private int countEntriesForAGivenDayInADirection(List<VehicleEntry> sessionEntries, int day, Direction direction) {
        return sessionEntries.stream().filter(entry -> entry.getDay() == day && entry.getDirection() == direction).collect(Collectors.toList()).size();
    }
}
