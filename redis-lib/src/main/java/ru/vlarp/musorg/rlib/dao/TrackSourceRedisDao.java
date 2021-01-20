package ru.vlarp.musorg.rlib.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.rlib.enums.RedisKeys;

import java.util.List;

@Component
public class TrackSourceRedisDao {
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public List<String> findAll() {
        return redisTemplate.boundListOps(RedisKeys.TRACK_SOURCE_SET).range(0, -1);
    }

    public void saveAll(List<String> trackSources) {
        redisTemplate.delete(RedisKeys.TRACK_SOURCE_SET);
        redisTemplate.boundListOps(RedisKeys.TRACK_SOURCE_SET).rightPushAll(trackSources.toArray(new String[0]));
    }
}
