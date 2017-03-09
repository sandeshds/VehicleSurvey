package com.sandesh.io;

import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

public class FileReaderTest {
    @Test
    public void shouldContainAllTheValidDataLines() throws Exception {
        URL testFilePath = ClassLoader.getSystemResource(com.sandesh.constants.Test.TEST_VEHICLE_DATA_FILE_PATH);
        List<String> dataLines = new FileReader().readLines(testFilePath);

        assertTrue(dataLines.contains("A98186"));
        assertTrue(dataLines.contains("A98333"));
        assertTrue(dataLines.contains("A499718"));
    }

    @Test
    public void shouldNotContainAllTheInValidDataLines() throws Exception {
        URL testFilePath = ClassLoader.getSystemResource(com.sandesh.constants.Test.TEST_VEHICLE_DATA_FILE_PATH);
        List<String> dataLines = new FileReader().readLines(testFilePath);

        assertTrue(!dataLines.contains("1234"));
    }

}