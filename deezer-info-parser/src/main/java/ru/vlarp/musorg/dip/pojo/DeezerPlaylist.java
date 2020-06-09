package ru.vlarp.musorg.dip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerPlaylist {
    public Long id;
    public DeezerTracks tracks;
}
