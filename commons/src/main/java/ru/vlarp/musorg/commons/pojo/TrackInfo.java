package ru.vlarp.musorg.commons.pojo;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TrackInfo {
    private String artist;
    private String album;
    private String title;
    private String sources;

    public boolean isAnyEmpty() {
        return StringUtils.isAnyEmpty(title, sources);
    }
}
