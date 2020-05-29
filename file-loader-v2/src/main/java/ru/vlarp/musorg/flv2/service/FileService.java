package ru.vlarp.musorg.flv2.service;

import org.springframework.stereotype.Component;
import ru.vlarp.musorg.flv2.dto.TrackSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

@Component
public class FileService {

    public TrackSource convertToTrackSource(String filePath) {
        TrackSource trackSource = null;
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

            trackSource = new TrackSource();
            trackSource.setName(path.toString());
            trackSource.setDescription(String.format("read file:%s", path));
            trackSource.setCreationTime(attributes.creationTime().toMillis());
            trackSource.setLastModifiedTime(attributes.lastModifiedTime().toMillis());

            return trackSource;
        } catch (Exception ex) {
//todo
        }

        return null;
    }
}
