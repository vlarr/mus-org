package ru.vlarp.musorg.flv2.parsers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vlarp.musorg.flv2.dto.Track;

import java.util.Iterator;

public class Parser1Impl extends ParserBase {
    private static final Logger log = LoggerFactory.getLogger(Parser1Impl.class);


    private final String separator;

    public Parser1Impl() {
        this.separator = " – ";
    }

    public Parser1Impl(String separator) {
        this.separator = separator;
    }

    private Track tryParse(String recordString) {
        String[] splitResult = StringUtils.splitByWholeSeparator(recordString, separator);

        if (splitResult.length != 2 || StringUtils.isAnyEmpty(splitResult)) {
          log.error("Error parse string: {}", recordString);
          return null;
        }

        Track track = new Track();
        track.setArtist(splitResult[0]);
        track.setTitle(splitResult[1]);
        return track;
    }


    @Override
    protected Track processLines(Iterator<String> lineIterator) {
        while (lineIterator.hasNext()) {
            Track track = tryParse(lineIterator.next());
            if (track != null) {
                return track;
            }
        }
        return null;
    }
}
