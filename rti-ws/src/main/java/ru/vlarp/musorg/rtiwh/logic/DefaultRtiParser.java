package ru.vlarp.musorg.rtiwh.logic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.vlarp.musorg.rtiwh.enums.RtiParseStatus;
import ru.vlarp.musorg.rtiwh.pojo.RtiParseResult;

@Slf4j
public class DefaultRtiParser implements RtiParser {
    @Override
    public RtiParseResult tryParseTrackInfo(String rawTrackInfo) {
        log.info(rawTrackInfo);

        String[] splitStr = StringUtils.split(rawTrackInfo, "-—");

        if (splitStr.length == 2) {
            splitStr[0] = StringUtils.trim(splitStr[0]);
            splitStr[1] = StringUtils.trim(splitStr[1]);

            RtiParseResult result = new RtiParseResult(splitStr[0], null, splitStr[1], RtiParseStatus.OK);
            log.info("parsed:{}", result);
            return result;
        }

        return new RtiParseResult(null, null, null, RtiParseStatus.ERROR);
    }
}
