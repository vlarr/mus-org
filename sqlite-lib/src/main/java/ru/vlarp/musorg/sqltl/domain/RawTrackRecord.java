package ru.vlarp.musorg.sqltl.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawTrackRecord {
    private Long id;
    private String artist;
    private String title;
    private String album;
    private Long trackSourceId;
    private Long creationTime;
    private Long state;
}
