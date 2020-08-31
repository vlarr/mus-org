package ru.vlarp.musorg.sqltl.service;


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
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.sqltl.dao.RawTrackDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;
import ru.vlarp.musorg.sqltl.domain.RawTrackRecord;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    private TrackSourceDao trackSourceDao;
    private RawTrackDao rawTrackDao;
    private RawInfoService rawInfoService;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setRawTrackDao(RawTrackDao rawTrackDao) {
        this.rawTrackDao = rawTrackDao;
    }

    @Autowired
    public void setRawInfoService(RawInfoService rawInfoService) {
        this.rawInfoService = rawInfoService;
    }

    @Before
    public void setUp() {
        Mockito.reset(trackSourceDao, rawTrackDao);
    }

    @Test
    public void consumeTrackInfos() {
        //given
        Mockito.when(trackSourceDao.findIdByName("SOURCE1")).thenReturn(1L);
        Mockito.when(trackSourceDao.findIdByName("SOURCE2")).thenReturn(2L);

        RawTrackInfo rawTrackInfo = new RawTrackInfo("test1", null, "test2", "SOURCE1,SOURCE2");

        //when
        rawInfoService.consumeTrackInfos(Collections.singletonList(rawTrackInfo));

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