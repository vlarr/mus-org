package ru.vlarp.musorg.rtiwh.logic;

import ru.vlarp.musorg.rtiwh.pojo.RtiParseResult;

public interface RtiParser {
    RtiParseResult tryParseTrackInfo(String rawTrackInfo);
}
