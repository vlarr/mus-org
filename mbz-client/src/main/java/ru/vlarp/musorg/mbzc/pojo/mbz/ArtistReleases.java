package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistReleases {
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReleaseEntry {
        private String title;
        private String id;
    }

    private List<ReleaseEntry> releases;

    @JsonProperty("release-offset")
    private Integer releaseOffset;
    @JsonProperty("release-count")
    private Integer releaseCount;
}
