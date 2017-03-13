package com.sandesh.model;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SessionTest {

    @Test
    public void shouldCreateSessionsWithGivenInterval() throws Exception {
        List<Session> output = Session.createWithInterval(60);

        assertEquals(24, output.size());
        assertEquals(new Date(0), output.get(0).startTime);
        assertEquals(new Date(3600000), output.get(0).endTime);
        assertEquals(new Date(82800000), output.get(23).startTime);
        assertEquals(new Date(86400000), output.get(23).endTime);
    }

    @Test
    public void shouldGiveEmptyListForUnEvenInterval() throws Exception {
        List<Session> output = Session.createWithInterval(25);

        assertEquals(0, output.size());
    }
}