package ru.vlarp.musorg.rtic.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.vlarp.musorg.commons.pojo.RmiMessage;
import ru.vlarp.musorg.sqltl.dao.RawMusInfoDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class RmiMessageServiceTest {

    @Mock
    private TrackSourceDao trackSourceDao;

    @Mock
    public RawMusInfoDao rawMusInfoDao;

    @InjectMocks
    public RmiMessageService rmiMessageService = new RmiMessageService();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processRmiMessage() {
        //given
        Mockito.when(trackSourceDao.findIdByName("SOURCE1")).thenReturn(1L);
        Mockito.when(trackSourceDao.findIdByName("SOURCE2")).thenReturn(2L);

        RmiMessage rmiMessage = new RmiMessage("test1", "test2", "test3", "SOURCE1,SOURCE2", null);

        //when
        rmiMessageService.processRmiMessage(rmiMessage);

        //then
        Mockito.verify(rawMusInfoDao, Mockito.times(1)).save(eq("test1"), eq("test2"), eq("test3"), eq(1L), anyLong(), eq(null));
        Mockito.verify(rawMusInfoDao, Mockito.times(1)).save(eq("test1"), eq("test2"), eq("test3"), eq(2L), anyLong(), eq(null));
    }
}