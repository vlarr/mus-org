package ru.vlarp.musorg.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RtiMessage {
    private String artist;
    private String album;
    private String track;
    private String sources;
    private String tags;
}
