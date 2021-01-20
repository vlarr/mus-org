package ru.vlarp.musorg.rmip.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RawMusInfoRecord {
    private Long id;
    private String artist;
    private String album;
    private String track;
    private Long sourceId;
    private Long dateCreate;
    private String tags;
}
