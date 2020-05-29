package ru.vlarp.musorg.flv2.parsers;

import ru.vlarp.musorg.flv2.dto.Track;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ParserBase {

    protected abstract Track processLines(Iterator<String> lineIterator);

    private List<Track> tryParse(List<String> allLines) {
        List<Track> result = new ArrayList<>();
        Iterator<String> lineIterator = allLines.iterator();

        while (true) {
            Track trackInfo = processLines(lineIterator);
            if (trackInfo == null) {
                break;
            } else {
                result.add(trackInfo);
            }
        }

        return result;
    }

    public List<Track> readFile(String filePath) {
        List<String> allLines = null;
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                allLines = Files.readAllLines(path);
            }
        } catch (Exception ex) {
            return null;
        }

        if (allLines == null || allLines.size() == 0) {
            return null;
        }

        return tryParse(allLines);
    }
}
