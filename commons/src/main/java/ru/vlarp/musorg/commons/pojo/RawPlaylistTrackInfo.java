package ru.vlarp.musorg.commons.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RawPlaylistTrackInfo {
    private String artist;
    private String title;
    private Long trackSourceId;
    private Long creationTime;
}