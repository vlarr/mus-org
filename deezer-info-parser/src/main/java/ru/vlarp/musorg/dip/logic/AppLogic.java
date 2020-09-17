package ru.vlarp.musorg.dip.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vlarp.musorg.commons.pojo.RmiMessage;
import ru.vlarp.musorg.commons.utils.JsonUtils;
import ru.vlarp.musorg.dip.pojo.DeezerTrack;
import ru.vlarp.musorg.dip.pojo.DeezerTracks;
import ru.vlarp.musorg.rmql.utils.TopicNameList;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AppLogic {
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    int processDeezerTracksPart(DeezerTracks response, String playlistName,
                                List<RmiMessage> resultRawTrackInfoList) {
        if (response.data != null) {
            for (DeezerTrack track : response.data) {
                resultRawTrackInfoList.add(
                        RmiMessage
                                .builder()
                                .artist((track.artist != null) ? track.artist.name : null)
                                .album((track.album != null) ? track.album.title : null)
                                .track(track.title)
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

        List<RmiMessage> resultRawTrackInfoList = new ArrayList<>();

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

        for (RmiMessage rawTrackInfo : resultRawTrackInfoList) {
            rabbitTemplate.convertAndSend(TopicNameList.RAW_TRACK_INFO, JsonUtils.toJSON(rawTrackInfo));
        }
        return 0;
    }
}
