package ru.vlarp.musorg.dip.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import ru.vlarp.musorg.dip.pojo.DeezerPlaylist;
import ru.vlarp.musorg.dip.pojo.DeezerTrack;
import ru.vlarp.musorg.dip.pojo.DeezerTracks;

class AppLogicTest {
    private static final Logger log = LoggerFactory.getLogger(AppLogicTest.class);


//    @Test
    void tryGet() throws InterruptedException {

//        RestTemplate restTemplate = new RestTemplate();
//
//        int totalCount = 0;
//
//        DeezerTracks response = null;
//        do {
//            String next = (response == null) ? "https://api.deezer.com/playlist/6748155604/tracks" : response.next;
//
//            response = restTemplate.getForObject(next, DeezerTracks.class);
//
//            if (response == null) {
//                return;
//            }
//
//            totalCount += response.data.size();
//            log.info("totalCount:{}, count:{}", totalCount, response.data.size());
//
//            if (response.data != null) {
//                for (DeezerTrack track : response.data) {
//                    log.info("artist:{}, title:{}", (track.artist != null) ? track.artist.name : "", track.title);
//                }
//            }
//
//            Thread.sleep(100L);
//        } while (response.next != null);
    }

//    @Test
    void tryGet2() {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//
//        DeezerPlaylist response = restTemplate.getForObject("https://api.deezer.com/playlist/6748155604", DeezerPlaylist.class);
//
//        if (response == null) {
//            return;
//        }
//
//        log.info("{}", response.id);
//
//        log.info("count:{}", response.tracks.data.size());
//        response.tracks.data.forEach(track -> log.info(track.title));
    }
}