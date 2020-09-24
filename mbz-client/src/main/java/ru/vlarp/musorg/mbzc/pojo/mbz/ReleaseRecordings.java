package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleaseRecordings {
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RecordingEntry {
        private String title;
        private String id;
    }

    @JsonProperty("recording-count")
    private Integer recordingCount;
    @JsonProperty("recording-offset")
    private Integer recordingOffset;
    private List<RecordingEntry> recordings;
}
