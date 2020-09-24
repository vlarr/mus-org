package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ArtistReleasesTest {
    private static final String REQUEST = "https://musicbrainz.org/ws/2/release?artist=9e10ee30-66d4-4599-a0f6-a8298106d9e6&fmt=json";

    @Test
    public void objectMapperTest() throws IOException {
        ArtistReleases response = new ObjectMapper().readValue(new ClassPathResource("example_release_of_artist.json").getFile(), ArtistReleases.class);
        assertEquals(Integer.valueOf(0), response.getReleaseOffset());
        assertEquals(Integer.valueOf(5), response.getReleaseCount());
        assertEquals(5, response.getReleases().size());
        assertEquals("2669fd1f-d212-4a7c-b4f9-2146df4857ca", response.getReleases().get(0).getId());
    }
}