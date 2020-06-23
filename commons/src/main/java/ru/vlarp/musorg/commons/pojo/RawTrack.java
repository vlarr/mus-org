package ru.vlarp.musorg.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawTrack {
    private String artist;
    private String title;
    private String album;
    private Long trackSourceId;
    private Long creationTime;
}
