package ru.vlarp.musorg.mbzc.logic;

import ru.vlarp.musorg.mbzc.pojo.mbz.*;

public interface ProcessorApi {
    ProcessorApiResponse<ArtistSearch> artistSearch(String name, Integer offset);

    ProcessorApiResponse<ArtistReleases> findArtistReleases(String artistId, Integer offset);

    ProcessorApiResponse<Artist> findArtist(String artistId);

    ProcessorApiResponse<Release> findRelease(String releaseId);

    ProcessorApiResponse<ReleaseRecordings> findReleaseRecordings(String releaseId, Integer offset);

    boolean readyForInteract();
}
