package ru.vlarp.musorg.rtiwh.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vlarp.musorg.rtiwh.pojo.ParseTrackInfoResult;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Import(AppLogic.class)
public class AppLogicTest {
    private AppLogic appLogic;

    @Autowired
    public void setAppLogic(AppLogic appLogic) {
        this.appLogic = appLogic;
    }

    @Test
    public void tryParseTrackInfo() {
        //given, when
        ParseTrackInfoResult result = appLogic.tryParseTrackInfo("test1 - test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals("OK", result.getStatus());

        //given, when
        result = appLogic.tryParseTrackInfo("test1 — test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals("OK", result.getStatus());

        //given, when
        result = appLogic.tryParseTrackInfo("test1 - test2 - test3");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());

        //given, when
        result = appLogic.tryParseTrackInfo("test1");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());

        //given, when
        result = appLogic.tryParseTrackInfo("  ");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals("ERROR", result.getStatus());
    }
}