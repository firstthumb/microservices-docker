package com.ekocaman.microservices.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisLeaderboardRepository {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public RedisLeaderboardRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void deleteLeaderboard(String leaderboardName) {
        redisTemplate.delete(leaderboardName);
    }

    public long saveScore(final String leaderboardName, final String player, final double score) {
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForZSet().add(leaderboardName, player, score);

                return operations.exec();
            }
        });

        return 1l;
    }
}
