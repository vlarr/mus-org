package ru.vlarp.musorg.rtiwh.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vlarp.musorg.rtiwh.enums.RtiParseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RtiParseResult {
    private String artist;
    private String album;
    private String title;
    private RtiParseStatus status;
}
