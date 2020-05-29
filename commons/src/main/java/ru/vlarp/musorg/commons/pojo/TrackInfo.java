package ru.vlarp.musorg.commons.pojo;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TrackInfo {
    private String artist;
    private String title;
    private String sources;

    public boolean checkAllNotNull() {
        return ObjectUtils.allNotNull(artist, title, sources);
    }
}
