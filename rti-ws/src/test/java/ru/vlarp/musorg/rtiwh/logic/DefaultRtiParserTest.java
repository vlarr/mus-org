package ru.vlarp.musorg.rtiwh.logic;

import org.junit.Test;
import ru.vlarp.musorg.rtiwh.enums.RtiParseStatus;
import ru.vlarp.musorg.rtiwh.pojo.RtiParseResult;

import static org.junit.Assert.*;

public class DefaultRtiParserTest {

    private final RtiParser rtiParser = new DefaultRtiParser();

    @Test
    public void tryParseTrackInfo() {
        //given, when
        RtiParseResult result = rtiParser.tryParseTrackInfo("test1 - test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals(RtiParseStatus.OK, result.getStatus());

        //given, when
        result = rtiParser.tryParseTrackInfo("test1 — test2");
        //then
        assertNotNull(result);
        assertEquals("test1", result.getArtist());
        assertEquals("test2", result.getTitle());
        assertEquals(RtiParseStatus.OK, result.getStatus());

        //given, when
        result = rtiParser.tryParseTrackInfo("test1 - test2 - test3");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals(RtiParseStatus.ERROR, result.getStatus());

        //given, when
        result = rtiParser.tryParseTrackInfo("test1");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals(RtiParseStatus.ERROR, result.getStatus());

        //given, when
        result = rtiParser.tryParseTrackInfo("  ");
        //then
        assertNotNull(result);
        assertNull(result.getArtist());
        assertNull(result.getTitle());
        assertEquals(RtiParseStatus.ERROR, result.getStatus());
    }
}