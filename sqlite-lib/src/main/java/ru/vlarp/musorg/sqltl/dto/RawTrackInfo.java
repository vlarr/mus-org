package ru.vlarp.musorg.sqltl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawTrackInfo {
    private Long id;
    private String artist;
    private String album;
    private String track;
    private Long sourceId;
    private Long dateCreate;
    private Long state;
    private String tags;
}
