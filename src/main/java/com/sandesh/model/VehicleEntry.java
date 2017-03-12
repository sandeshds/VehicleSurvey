package com.sandesh.model;

import com.sandesh.constants.TimeConstants;

import java.util.Date;

public class VehicleEntry {
    private final int frontAxleTime;
    private final int rearAxleTime;
    private final Direction direction;
    private int day;

    public VehicleEntry(int frontAxleTime, int rearAxleTime, Direction direction, int day) {
        this.frontAxleTime = frontAxleTime;
        this.rearAxleTime = rearAxleTime;
        this.direction = direction;
    }

    public boolean isValidEntry(){
        return frontAxleTime >= 0
                && frontAxleTime < TimeConstants.MAX_MILLISECONDS_IN_A_DAY
                && rearAxleTime >= 0
                && rearAxleTime < TimeConstants.MAX_MILLISECONDS_IN_A_DAY
                && frontAxleTime < rearAxleTime;
    }

    public Date entryTime(){
        return new Date((frontAxleTime + rearAxleTime)/2);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDay() {
        return day;
    }
}
