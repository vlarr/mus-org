package ru.vlarp.musorg.dip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerTrack {
    public String title;
    public DeezerArtist artist;
    public DeezerAlbum album;
}
