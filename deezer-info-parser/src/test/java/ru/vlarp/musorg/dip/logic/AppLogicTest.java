package ru.vlarp.musorg.dip.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.commons.service.RawInfoService;
import ru.vlarp.musorg.dip.pojo.DeezerTracks;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

@Slf4j
@RunWith(SpringRunner.class)
public class AppLogicTest {
    @TestConfiguration
    @Import(AppLogic.class)
    static class TestContextConfiguration {
        @MockBean
        public RawInfoService rawInfoService;
    }

    @Autowired
    private AppLogic appLogic;

    @Test
    public void processDeezerTracksPartTest() throws IOException {
        //  Given
        ObjectMapper objectMapper = new ObjectMapper();
        DeezerTracks response = objectMapper.readValue(new ClassPathResource("example.json").getFile(), DeezerTracks.class);

        ArrayList<RawTrackInfo> result = new ArrayList<>();

        //  When
        int processedTracks = appLogic.processDeezerTracksPart(response, "playlist1", result);

        //  Then
        assertEquals(25, processedTracks);
        assertEquals(25, result.size());

        RawTrackInfo actualRawTrackInfo0 = result.get(0);
        RawTrackInfo expectedRawTrackInfo0 = RawTrackInfo
                .builder()
                .sources("playlist1")
                .album("Passages")
                .artist("Aes Dana")
                .title("Unlit")
                .build();
        assertEquals(expectedRawTrackInfo0, actualRawTrackInfo0);
    }
}