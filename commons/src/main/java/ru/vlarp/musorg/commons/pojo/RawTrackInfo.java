package ru.vlarp.musorg.commons.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RawTrackInfo {
    private String artist;
    private String album;
    private String title;
    private String sources;

    @JsonIgnore
    public boolean isAnyEmpty() {
        return StringUtils.isAnyEmpty(title, sources);
    }
}
