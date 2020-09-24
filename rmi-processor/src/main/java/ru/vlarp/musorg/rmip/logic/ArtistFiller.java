package ru.vlarp.musorg.rmip.logic;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.sqltl.dao.RawArtistDao;
import ru.vlarp.musorg.sqltl.dao.RawMusInfoDao;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class ArtistFiller {
    private RawArtistDao rawArtistDao;
    private RawMusInfoDao rawMusInfoDao;

    @Autowired
    public void setRawArtistDao(RawArtistDao rawArtistDao) {
        this.rawArtistDao = rawArtistDao;
    }

    @Autowired
    public void setRawMusInfoDao(RawMusInfoDao rawMusInfoDao) {
        this.rawMusInfoDao = rawMusInfoDao;
    }

    @PostConstruct
    public void init() {
        rawMusInfoDao.findWithNullState(10000L)
                .stream()
                .filter(rmi -> ObjectUtils.allNotNull(rmi.getArtist()) && rmi.getAlbum() == null && rmi.getTrack() == null)
                .limit(100)
                .forEach(rmi -> log.info("{}", rmi));
    }
}
