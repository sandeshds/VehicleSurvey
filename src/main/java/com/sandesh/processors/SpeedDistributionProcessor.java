package com.sandesh.processors;


import com.sandesh.model.Session;
import com.sandesh.model.VehicleEntry;

import java.util.List;

public class SpeedDistributionProcessor extends BaseProcessor {
    private final int sessionInterval;

    public SpeedDistributionProcessor(int sessionInterval) {
        this.sessionInterval = sessionInterval;
    }

    @Override
    public String processData(List<VehicleEntry> entries) {
        StringBuilder stringBuilder = new StringBuilder();

        List<Session> sessions = Session.createWithInterval(sessionInterval);
        for (Session session : sessions){
            List<VehicleEntry> entriesInTheSession = getEntriesInSession(entries, session);
            double totalSpeed = entriesInTheSession.stream().mapToDouble(VehicleEntry::speedInKMPH).sum();
            double averageSpeed = 0;
            if (!entriesInTheSession.isEmpty())
                averageSpeed = totalSpeed/entriesInTheSession.size();
            stringBuilder.append(session).append(" || Average speed = ").append(averageSpeed).append('\n');
        }
        return stringBuilder.toString();
    }
}
