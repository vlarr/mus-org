package ru.vlarp.musorg.mbzc.service;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vlarp.musorg.mbzc.pojo.mbz.Artist;
import ru.vlarp.musorg.mbzc.pojo.mbz.ArtistReleases;
import ru.vlarp.musorg.mbzc.pojo.mbz.Release;
import ru.vlarp.musorg.mbzc.pojo.mbz.ReleaseRecordings;

import java.util.List;

@Component
public class MbzService {
    private static final String ARTIST_REQUEST = "https://musicbrainz.org/ws/2/artist/%s?fmt=json";

    public List<Artist> artistSearch(String name) {
        throw new NotImplementedException("");
    }

    public ArtistReleases findArtistReleases(String artistId) {
        throw new NotImplementedException("");
    }

    public Artist findArtist(String artistId) {
        throw new NotImplementedException("");
    }

    public Release findRelease(String releaseId) {
        throw new NotImplementedException("");
    }

    public ReleaseRecordings findReleaseRecordings(String releaseId) {
        throw new NotImplementedException("");
    }
}
