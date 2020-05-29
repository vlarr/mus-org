package ru.vlarp.musorg.flv2.parsers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vlarp.musorg.flv2.dto.Track;

import java.util.Iterator;

public class Parser2Impl extends ParserBase {
    private static final Logger log = LoggerFactory.getLogger(Parser1Impl.class);

    @Override
    protected Track processLines(Iterator<String> lineIterator) {
        Track track = new Track();

        if (lineIterator.hasNext()) {
            String artist = lineIterator.next();
            if (!StringUtils.isEmpty(artist)) {
                track.setArtist(artist);
            }
        } else {
            return null;
        }

        if (lineIterator.hasNext()) {
            String title = lineIterator.next();
            if (!StringUtils.isEmpty(title)) {
                track.setTitle(title);
            }
        } else {
            return null;
        }

        if (lineIterator.hasNext()) {
            String length = lineIterator.next();
            if (!StringUtils.isEmpty(length)) {

            }
        }

        if (lineIterator.hasNext()) {
            lineIterator.next();
        }

        return track;
    }
}
