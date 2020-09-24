package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ArtistTest {
    private static final String REQUEST = "https://musicbrainz.org/ws/2/artist/9e10ee30-66d4-4599-a0f6-a8298106d9e6?fmt=json";

    @Test
    public void objectMapperTest() throws IOException {
        Artist response = new ObjectMapper().readValue(new ClassPathResource("example_artist.json").getFile(), Artist.class);
        assertEquals("9e10ee30-66d4-4599-a0f6-a8298106d9e6", response.getId());
    }
}