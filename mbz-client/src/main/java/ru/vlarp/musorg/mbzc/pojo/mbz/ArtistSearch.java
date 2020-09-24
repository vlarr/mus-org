package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistSearch {
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArtistEntry {
        private String id;
        private String name;
        @JsonProperty("sort-name")
        private String sortName;
    }

    private Integer count;
    private Integer offset;
    private List<ArtistEntry> artists;
}
