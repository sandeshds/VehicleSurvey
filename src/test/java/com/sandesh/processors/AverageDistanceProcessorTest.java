package com.sandesh.processors;

import com.sandesh.model.Direction;
import com.sandesh.model.VehicleEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AverageDistanceProcessorTest {
    private List<VehicleEntry> entries = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        entries.add(new VehicleEntry(100,200, Direction.NORTH,0));
        entries.add(new VehicleEntry(300,400, Direction.NORTH,0));
        entries.add(new VehicleEntry(500,600, Direction.NORTH,0));
        entries.add(new VehicleEntry(4100,4200, Direction.NORTH,0));
        entries.add(new VehicleEntry(100,200, Direction.SOUTH,1));
        entries.add(new VehicleEntry(300,400, Direction.SOUTH,1));
        entries.add(new VehicleEntry(3100,3200, Direction.SOUTH,1));
    }

    @Test
    public void shouldGiveSessionviceAverageDistance() throws Exception {
        AverageDistanceProcessor processor = new AverageDistanceProcessor(360);
        String output = processor.processData(entries);

        String expectedOutput = "Session 00:00:00 to 06:00:00 Average distance between the cars travelling in southern direction = 16.6667 meters, in northern direction = 16.6667 meters\n" +
                "Session 06:00:00 to 12:00:00 Average distance between the cars travelling in southern direction = 0.0 meters, in northern direction = 0.0 meters\n" +
                "Session 12:00:00 to 18:00:00 Average distance between the cars travelling in southern direction = 0.0 meters, in northern direction = 0.0 meters\n" +
                "Session 18:00:00 to 00:00:00 Average distance between the cars travelling in southern direction = 0.0 meters, in northern direction = 0.0 meters\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void shouldGiveAverageDistanceAsZeroIFNoTrafficForSession() throws Exception {
        AverageDistanceProcessor processor = new AverageDistanceProcessor(360);
        String output = processor.processData(entries);

        assertTrue(output.contains("Session 12:00:00 to 18:00:00 Average distance between the cars travelling in southern direction = 0.0 meters, in northern direction = 0.0 meters"));
        assertTrue(output.contains("Session 18:00:00 to 00:00:00 Average distance between the cars travelling in southern direction = 0.0 meters, in northern direction = 0.0 meters"));
    }

    @Test
    public void shouldGiveAverageDistanceForBothDirections() throws Exception {
        AverageDistanceProcessor processor = new AverageDistanceProcessor(360);
        String output = processor.processData(entries);

        assertTrue(output.contains("southern direction = 16.6667 meters, in northern direction = 16.6667 meters"));
    }

    @Test
    public void shouldNotGiveOutputIfSessionIntervalIsNotEvenlyDistributed() throws Exception {
        AverageDistanceProcessor processor = new AverageDistanceProcessor(25);
        String output = processor.processData(entries);

        assertEquals("", output);
    }
}