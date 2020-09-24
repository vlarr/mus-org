package ru.vlarp.musorg.dip.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.ClassPathResource;
import ru.vlarp.musorg.commons.pojo.RmiMessage;
import ru.vlarp.musorg.dip.pojo.DeezerTracks;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class AppLogicTest {
    public AppLogic appLogic = spy(new AppLogic());

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processDeezerTracksPartTest() throws IOException {
        //  Given
        ObjectMapper objectMapper = new ObjectMapper();
        DeezerTracks response = objectMapper.readValue(new ClassPathResource("example.json").getFile(), DeezerTracks.class);

        ArrayList<RmiMessage> result = new ArrayList<>();

        //  When
        int processedTracks = appLogic.processDeezerTracksPart(response, "playlist1", result);

        //  Then
        assertEquals(25, processedTracks);
        assertEquals(25, result.size());

        RmiMessage actualRawTrackInfo0 = result.get(0);
        RmiMessage expectedRawTrackInfo0 = RmiMessage
                .builder()
                .sources("playlist1")
                .album("Passages")
                .artist("Aes Dana")
                .track("Unlit")
                .build();
        assertEquals(expectedRawTrackInfo0, actualRawTrackInfo0);
    }
}