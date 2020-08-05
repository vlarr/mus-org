package ru.vlarp.musorg.rlib.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import ru.vlarp.musorg.rlib.enums.RedisKeys;

import java.util.Set;

@Component
public class TrackSourceRedisDao {
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Set<String> findAll() {
        return redisTemplate.boundSetOps(RedisKeys.TRACK_SOURCE_SET).members();
    }

    public void saveAll(Set<String> trackSources) {
        redisTemplate.boundSetOps(RedisKeys.TRACK_SOURCE_SET).add(trackSources.toArray(new String[0]));
    }
}
