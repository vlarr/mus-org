package ru.vlarp.musorg.commons.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
