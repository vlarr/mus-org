package ru.vlarp.musorg.rtiwh.logic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.commons.pojo.ParseTrackInfoResult;

@Component
@Slf4j
public class AppLogic {
    public ParseTrackInfoResult tryParseTrackInfo(String rawTrackInfo) {
        log.info(rawTrackInfo);

        String[] splitStr = StringUtils.split(rawTrackInfo, "-—");

        if (splitStr.length == 2) {
            splitStr[0] = StringUtils.trim(splitStr[0]);
            splitStr[1] = StringUtils.trim(splitStr[1]);

            ParseTrackInfoResult result = new ParseTrackInfoResult(splitStr[0], null, splitStr[1], "OK");
            log.info("parsed:{}", result);
            return result;
        }

        return new ParseTrackInfoResult(null, null, null, "ERROR");
    }
}
