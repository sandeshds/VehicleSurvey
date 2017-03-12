package com.sandesh.model;

import com.sandesh.constants.TimeConstants;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class VehicleEntryTest {

    @Test
    public void shouldReturnFalseForFrontAxleTimeLessThanZero() throws Exception {
        VehicleEntry output = new VehicleEntry(-1, 1, Direction.SOUTH, 0);
        assertFalse(output.isValidEntry());
    }

    @Test
    public void shouldReturnFalseForFrontAxleTimeGreaterThanMaxTime() throws Exception {
        VehicleEntry output = new VehicleEntry(TimeConstants.MAX_MILLISECONDS_IN_A_DAY, 1, Direction.SOUTH, 0);
        assertFalse(output.isValidEntry());
    }

    @Test
    public void shouldReturnFalseForRearAxleTimeLessThanZero() throws Exception {
        VehicleEntry output = new VehicleEntry(1, -1, Direction.SOUTH, 0);
        assertFalse(output.isValidEntry());
    }

    @Test
    public void shouldReturnFalseForRearAxleTimeGreaterThanMaxTime() throws Exception {
        VehicleEntry output = new VehicleEntry(1, TimeConstants.MAX_MILLISECONDS_IN_A_DAY, Direction.SOUTH, 0);
        assertFalse(output.isValidEntry());
    }

    @Test
    public void shouldReturnFalseForFrontAxleTimeGreaterThanRearAxleTime() throws Exception {
        VehicleEntry output = new VehicleEntry(2,1,Direction.SOUTH, 0);
        assertFalse(output.isValidEntry());
    }

    @Test
    public void shouldReturnTrueForValidData() throws Exception {
        VehicleEntry output = new VehicleEntry(1,2,Direction.SOUTH, 0);
        assertTrue(output.isValidEntry());
    }

    @Test
    public void shouldReturnTimeOfEntry() throws Exception {
        VehicleEntry output = new VehicleEntry(1000, 2000, Direction.SOUTH, 0);
        Date expected = new Date(1500);
        assertEquals(expected, output.entryTime());
    }
}