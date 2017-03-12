package com.sandesh.parser;

import com.sandesh.model.Direction;
import com.sandesh.model.VehicleEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VehicleDataParserTest {
    @Test
    public void shouldCreateEntriesForNorthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234", "A1236", "A1237", "A1238"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertEquals(Direction.NORTH, result.get(0).getDirection());
        assertEquals(Direction.NORTH, result.get(1).getDirection());
        assertEquals(2, result.size());
    }

    @Test
    public void shouldCreateMultipleEntriesInSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234", "B1236", "A1345", "B1356", "A1357", "B1358", "A1359", "B1360"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertEquals(Direction.SOUTH, result.get(0).getDirection());
        assertEquals(Direction.SOUTH, result.get(1).getDirection());
        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnEmptyListForInsufficientNumberOfInputsInNorthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234", "A1236", "A1345"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForInsufficientNumberOfInputsInSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234", "B1236", "A1345", "B1236", "A1345", "B1236", "A1345"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForInsufficientNumberOfInputsToFIndDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForInvalidOrderOfInputsInSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A1234", "B1236", "A1345", "A1236"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyListForInvalidTimeInSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A86400001", "B86400001", "A86400001", "B86400001"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }


    @Test
    public void shouldReturnEmptyListForInvalidTimeInNorthDirection2() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A86400001", "A86400001"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertTrue(result.isEmpty());
    }


    @Test
    public void shouldIncrementDayIfOneEntryTimeIsLessThanPreviousEntryTimeOnNorthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A2000", "A2005", "A1000", "A1005"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertEquals(0,result.get(0).getDay());
        assertEquals(1,result.get(1).getDay());
    }

    @Test
    public void shouldIncrementDayIfOneEntryTimeIsLessThanPreviousEntryTimeOnSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A2000", "B2005", "A2100", "B2105", "A1000", "B1005", "A1100", "B1105"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertEquals(0,result.get(0).getDay());
        assertEquals(1,result.get(1).getDay());
    }

    @Test
    public void shouldNotIncrementDayIfSecondEntryTimeIsGreaterThanPreviousEntryTimeOnNorthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A2000", "A2005", "A3000", "A3005"));
        List<VehicleEntry> result = new VehicleDataParser().parseLines(dataLines);

        assertEquals(0,result.get(0).getDay());
        assertEquals(0,result.get(1).getDay());
    }

    @Test
    public void shouldNotIncrementDayIfSecondEntryTimeIsGreaterThanPreviousEntryTimeOnSouthDirection() throws Exception {
        List<String> dataLines = new ArrayList<>(Arrays.asList("A2000", "B2005", "A2100", "B2105", "A3000", "B3005", "A3100", "B3105"));
        List<VehicleEntry> output = new VehicleDataParser().parseLines(dataLines);

        assertEquals(0,output.get(0).getDay());
        assertEquals(0,output.get(1).getDay());
    }
}