package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.*;

public class ArtistSearchTest {
    private static final String REQUEST = "https://musicbrainz.org/ws/2/artist/?query=Asura&fmt=json";

    @Test
    public void objectMapperTest() throws IOException {
        ArtistSearch response = new ObjectMapper().readValue(new ClassPathResource("example_artist_search.json").getFile(), ArtistSearch.class);
        assertEquals(25, response.getArtists().size());
        assertEquals("49a5f5b9-44f3-4db2-a9a8-db8773de4edc", response.getArtists().get(0).getId());
    }
}