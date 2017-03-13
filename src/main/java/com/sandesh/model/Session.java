package com.sandesh.model;

import com.sandesh.constants.TimeConstants;

import java.text.SimpleDateFormat;
import java.util.*;

public final class Session {
    public final Date startTime;
    public final Date endTime;

    private Session(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static List<Session> createWithInterval(int interval){
        List<Session> sessionList = new ArrayList<>();
        int minutesInADay = TimeConstants.HOURS_IN_A_DAY * TimeConstants.MINUTES_IN_HOUR;
        if (minutesInADay % interval != 0){
            System.out.println("Interval of " + interval + " minutes is not evenly distributable");
            return sessionList;
        }
        for (int i=0; i< minutesInADay/interval; i++){
            Date startTime = new Date(i * interval * TimeConstants.SECONDS_IN_A_MINUTE * TimeConstants.MILLISECONDS_IN_A_SECOND);
            Date endTime = new Date((i+1) * interval * TimeConstants.SECONDS_IN_A_MINUTE * TimeConstants.MILLISECONDS_IN_A_SECOND);

            sessionList.add(new Session(startTime,endTime));
        }
        return sessionList;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        dateFormat.setTimeZone(new SimpleTimeZone(SimpleTimeZone.UTC_TIME, "UTC"));

        return "Session " + dateFormat.format(startTime) + " to " + dateFormat.format(endTime);
    }
}
