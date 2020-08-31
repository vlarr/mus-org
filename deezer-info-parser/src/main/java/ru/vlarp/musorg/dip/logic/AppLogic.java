package ru.vlarp.musorg.dip.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vlarp.musorg.commons.pojo.RawTrackInfo;
import ru.vlarp.musorg.dip.pojo.DeezerTrack;
import ru.vlarp.musorg.dip.pojo.DeezerTracks;
import ru.vlarp.musorg.sqltl.service.RawInfoService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppLogic {
    private static final Logger log = LoggerFactory.getLogger(AppLogic.class);

    private RawInfoService rawInfoService;

    @Autowired
    public void setRawInfoService(RawInfoService rawInfoService) {
        this.rawInfoService = rawInfoService;
    }

    int processDeezerTracksPart(DeezerTracks response, String playlistName,
                                List<RawTrackInfo> resultRawTrackInfoList) {
        if (response.data != null) {
            for (DeezerTrack track : response.data) {
                resultRawTrackInfoList.add(
                        RawTrackInfo
                                .builder()
                                .artist((track.artist != null) ? track.artist.name : null)
                                .title(track.title)
                                .album((track.album != null) ? track.album.title : null)
                                .sources(playlistName)
                                .build()
                );
            }

            return response.data.size();
        } else {
            return 0;
        }
    }

    public Integer processDeezerPlaylist(Long playlistId, String playlistName) {
        RestTemplate restTemplate = new RestTemplate();

        List<RawTrackInfo> resultRawTrackInfoList = new ArrayList<>();

        int totalCount = 0;
        DeezerTracks response = null;
        try {
            do {
                String next = (response == null) ? String.format("https://api.deezer.com/playlist/%d/tracks", playlistId) : response.next;
                response = restTemplate.getForObject(next, DeezerTracks.class);
                if (response == null) {
                    break;
                }

                int processedTracks = processDeezerTracksPart(response, playlistName, resultRawTrackInfoList);
                totalCount += processedTracks;
                log.info("totalCount:{}, count:{}", totalCount, processedTracks);

                Thread.sleep(100L); // ограничение на 50 запросов в секунду, минимум 20 мсек между запросами. Установлено 100 мсек чтобы наверняка
            } while (response.next != null);
        } catch (InterruptedException ex) {
            return 1;
        }

        rawInfoService.consumeTrackInfos(resultRawTrackInfoList);

        return 0;
    }
}
