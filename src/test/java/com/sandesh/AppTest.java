package com.sandesh;

import com.sandesh.io.FileReader;
import org.junit.Test;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppTest {
    @Test public void testShouldcallFileReaderClass() {
        FileReader fileReader = mock(FileReader.class);
        new App(fileReader).run();
        when(fileReader.readLines(any(URL.class))).thenReturn(null);

        verify(fileReader).readLines(any(URL.class));
    }
}
