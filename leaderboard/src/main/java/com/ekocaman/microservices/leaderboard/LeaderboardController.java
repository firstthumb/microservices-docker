package com.ekocaman.microservices.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class LeaderboardController {
    protected Logger logger = Logger.getLogger(LeaderboardController.class.getName());

    protected RedisLeaderboardRepository leaderboardRepository;

    @Autowired
    public LeaderboardController(RedisLeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }

    @RequestMapping("/leaderboard/top")
    public String viewTop() {
        leaderboardRepository.saveScore("level1", "player1", 10);

        return "ok";
    }
}
