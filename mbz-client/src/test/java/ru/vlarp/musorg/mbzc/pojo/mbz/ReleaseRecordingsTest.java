package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.*;

public class ReleaseRecordingsTest {
    private static final String REQUEST = "https://musicbrainz.org/ws/2/recording?release=2669fd1f-d212-4a7c-b4f9-2146df4857ca&fmt=json";

    @Test
    public void objectMapperTest() throws IOException {
        ReleaseRecordings response = new ObjectMapper().readValue(new ClassPathResource("example_release_recordings.json").getFile(), ReleaseRecordings.class);

        assertEquals(Integer.valueOf(4), response.getRecordingCount());
        assertEquals(Integer.valueOf(0), response.getRecordingOffset());
        assertEquals(4, response.getRecordings().size());
        assertEquals("13f1bd8e-5346-4f09-9a2c-2fb86905ac29", response.getRecordings().get(0).getId());
    }
}