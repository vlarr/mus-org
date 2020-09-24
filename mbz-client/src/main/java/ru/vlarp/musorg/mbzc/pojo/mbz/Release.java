package ru.vlarp.musorg.mbzc.pojo.mbz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release {
    private String id;
    private String title;
}
