package ru.vlarp.musorg.riws.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.sqltl.dao.TrackSourceDao;
import ru.vlarp.musorg.rlib.dao.TrackSourceRedisDao;

import javax.annotation.PostConstruct;

@Component
public class AppLogic {
    private TrackSourceDao trackSourceDao;
    private TrackSourceRedisDao trackSourceRedisDao;

    @Autowired
    public void setTrackSourceDao(TrackSourceDao trackSourceDao) {
        this.trackSourceDao = trackSourceDao;
    }

    @Autowired
    public void setTrackSourceRedisDao(TrackSourceRedisDao trackSourceRedisDao) {
        this.trackSourceRedisDao = trackSourceRedisDao;
    }

    @PostConstruct
    public void uploadRedisData() {
        trackSourceRedisDao.saveAll(trackSourceDao.findActive());
    }
}
