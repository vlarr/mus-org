package ru.vlarp.musorg.dip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerTracks {
    public List<DeezerTrack> data;
    public String next;
}
