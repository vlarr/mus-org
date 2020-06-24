package ru.vlarp.musorg.commons.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParseTrackInfoResult {
    private String artist;
    private String title;
    private String status;
}
