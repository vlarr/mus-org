package ru.vlarp.musorg.dip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerArtist {
    public Long id;
    public String name;
}
