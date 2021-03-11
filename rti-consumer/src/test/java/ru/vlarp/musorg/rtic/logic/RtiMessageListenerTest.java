package ru.vlarp.musorg.rtic.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import ru.vlarp.musorg.commons.pojo.RtiMessage;
import ru.vlarp.musorg.sqltl.dao.RawTrackInfoSqltDao;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;

@RunWith(MockitoJUnitRunner.class)
public class RtiMessageListenerTest {

    @Mock
    private TrackSourceDao trackSourceDao;

    @Mock
    public RawTrackInfoSqltDao rawTrackInfoSqltDao;

    @InjectMocks
    public RtiMessageListener rtiMessageListener = new RtiMessageListener();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void processRmiMessage() {
        //given
        Mockito.when(trackSourceDao.findIdByName("SOURCE1")).thenReturn(1L);
        Mockito.when(trackSourceDao.findIdByName("SOURCE2")).thenReturn(2L);

        RtiMessage rtiMessage = new RtiMessage(
                "test1", "test2", "test3", "SOURCE1,SOURCE2", null
        );

        //when
        rtiMessageListener.processRmiMessage(rtiMessage);

        //then
        Mockito.verify(rawTrackInfoSqltDao, Mockito.times(1))
                .save(ArgumentMatchers.eq("test1"), ArgumentMatchers.eq("test2"), ArgumentMatchers.eq("test3"), ArgumentMatchers.eq(1L), ArgumentMatchers.anyLong(), ArgumentMatchers.eq(null));
        Mockito.verify(rawTrackInfoSqltDao, Mockito.times(1))
                .save(ArgumentMatchers.eq("test1"), ArgumentMatchers.eq("test2"), ArgumentMatchers.eq("test3"), ArgumentMatchers.eq(2L), ArgumentMatchers.anyLong(), ArgumentMatchers.eq(null));
    }
}