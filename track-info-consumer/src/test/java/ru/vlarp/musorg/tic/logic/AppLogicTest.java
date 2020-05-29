package ru.vlarp.musorg.tic.logic;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vlarp.musorg.commons.dao.RawPlaylistTrackInfoDao;
import ru.vlarp.musorg.commons.dao.TrackSourceDao;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawPlaylistTrackInfo;
import ru.vlarp.musorg.commons.pojo.TrackInfo;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AppLogicTest {
    @TestConfiguration
    static class TestContextConfiguration {
        @MockBean
        public TrackSourceDao trackSourceDao;

        @MockBean
        public RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao;

        @Bean
        public AppLogic appLogic() {
            return new AppLogic();
        }
    }

    @Autowired
    public TrackSourceDao trackSourceDao;

    @Autowired
    public RawPlaylistTrackInfoDao rawPlaylistTrackInfoDao;

    @Autowired
    private AppLogic logic;

    @Before
    public void setUp() {
        Mockito.reset(trackSourceDao, rawPlaylistTrackInfoDao);
    }

    @Test
    public void tryParseTrackInfo() {
        //given, when
        ParseTrackInfoResult result = logic.tryParseTrackInfo("test1 - test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals("OK", result.getStatus());

        //given, when
        result = logic.tryParseTrackInfo("  ");
        //then
        assertNotNull(result);
        assertNull( result.getArtist());
        assertNull( result.getTitle());
        assertEquals("ERROR", result.getStatus());
    }

    @Test
    public void consumeTrackInfos() {
        //given
        Mockito.when(trackSourceDao.findIdByName("SOURCE1")).thenReturn(1L);
        Mockito.when(trackSourceDao.findIdByName("SOURCE2")).thenReturn(2L);

        TrackInfo trackInfo = new TrackInfo("test1", "test2", "SOURCE1,SOURCE2");

        //when
        logic.consumeTrackInfos(Collections.singletonList(trackInfo));

        //then
        ArgumentCaptor<RawPlaylistTrackInfo> argumentCaptor = ArgumentCaptor.forClass(RawPlaylistTrackInfo.class);
        Mockito.verify(rawPlaylistTrackInfoDao, Mockito.times(2)).saveToStaging(argumentCaptor.capture());

        List<RawPlaylistTrackInfo> capturedRawPlaylistTrackInfo = argumentCaptor.getAllValues();

        assertEquals(2, capturedRawPlaylistTrackInfo.size());

        assertEquals("test1", capturedRawPlaylistTrackInfo.get(0).getArtist());
        assertEquals("test2", capturedRawPlaylistTrackInfo.get(0).getTitle());
        assertTrue(ImmutableSet.of(1L, 2L).contains(capturedRawPlaylistTrackInfo.get(0).getTrackSourceId()));
        assertEquals("test1", capturedRawPlaylistTrackInfo.get(1).getArtist());
        assertEquals("test2", capturedRawPlaylistTrackInfo.get(1).getTitle());
        assertTrue(ImmutableSet.of(1L, 2L).contains(capturedRawPlaylistTrackInfo.get(1).getTrackSourceId()));
    }
}