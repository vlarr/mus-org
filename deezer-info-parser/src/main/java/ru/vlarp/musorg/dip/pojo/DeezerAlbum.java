package ru.vlarp.musorg.dip.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeezerAlbum {
    public Long id;
    public String title;
}
