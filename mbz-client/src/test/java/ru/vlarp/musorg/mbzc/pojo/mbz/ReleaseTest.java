package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ReleaseTest {
    private static final String REQUEST = "https://musicbrainz.org/ws/2/release/4f9ea317-f23b-4138-851a-b5034b6efa69?fmt=json";

    @Test
    public void objectMapperTest() throws IOException {
        Release response = new ObjectMapper().readValue(new ClassPathResource("example_release.json").getFile(), Release.class);
        assertEquals("4f9ea317-f23b-4138-851a-b5034b6efa69", response.getId());
    }
}