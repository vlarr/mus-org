package ru.vlarp.musorg.commons.service;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vlarp.musorg.commons.dao.RawTrackDao;
import ru.vlarp.musorg.commons.dao.TrackSourceDao;
import ru.vlarp.musorg.commons.domain.RawTrackRecord;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;
import ru.vlarp.musorg.commons.pojo.RawTrack;
import ru.vlarp.musorg.commons.pojo.TrackInfo;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class RawInfoServiceTest {
    @TestConfiguration
    static class TestContextConfiguration {
        @MockBean
        public TrackSourceDao trackSourceDao;

        @MockBean
        public RawTrackDao rawTrackDao;

        @Bean
        public RawInfoService rawInfoService() {
            return new RawInfoService();
        }
    }

    @Autowired
    public TrackSourceDao trackSourceDao;

    @Autowired
    public RawTrackDao rawTrackDao;

    @Autowired
    private RawInfoService rawInfoService;

    @Before
    public void setUp() {
        Mockito.reset(trackSourceDao, rawTrackDao);
    }


    @Test
    public void tryParseTrackInfo() {
        //given, when
        ParseTrackInfoResult result = rawInfoService.tryParseTrackInfo("test1 - test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals("OK", result.getStatus());

        //given, when
        result = rawInfoService.tryParseTrackInfo("test1 — test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals("OK", result.getStatus());

        //given, when
        result = rawInfoService.tryParseTrackInfo("test1 - test2 - test3");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());

        //given, when
        result = rawInfoService.tryParseTrackInfo("test1");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());

        //given, when
        result = rawInfoService.tryParseTrackInfo("  ");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());
    }

    @Test
    public void consumeTrackInfos() {
        //given
        Mockito.when(trackSourceDao.findIdByName("SOURCE1")).thenReturn(1L);
        Mockito.when(trackSourceDao.findIdByName("SOURCE2")).thenReturn(2L);

        TrackInfo trackInfo = new TrackInfo("test1", null, "test2", "SOURCE1,SOURCE2");

        //when
        rawInfoService.consumeTrackInfos(Collections.singletonList(trackInfo));

        //then
        ArgumentCaptor<RawTrackRecord> argumentCaptor = ArgumentCaptor.forClass(RawTrackRecord.class);
        Mockito.verify(rawTrackDao, Mockito.times(2)).save(argumentCaptor.capture());

        List<RawTrackRecord> capturedRawPlaylistTrackInfo = argumentCaptor.getAllValues();

        assertEquals(2, capturedRawPlaylistTrackInfo.size());

        assertEquals("test1", capturedRawPlaylistTrackInfo.get(0).getArtist());
        assertEquals("test2", capturedRawPlaylistTrackInfo.get(0).getTitle());
        assertTrue(ImmutableSet.of(1L, 2L).contains(capturedRawPlaylistTrackInfo.get(0).getTrackSourceId()));
        assertEquals("test1", capturedRawPlaylistTrackInfo.get(1).getArtist());
        assertEquals("test2", capturedRawPlaylistTrackInfo.get(1).getTitle());
        assertTrue(ImmutableSet.of(1L, 2L).contains(capturedRawPlaylistTrackInfo.get(1).getTrackSourceId()));
    }


}