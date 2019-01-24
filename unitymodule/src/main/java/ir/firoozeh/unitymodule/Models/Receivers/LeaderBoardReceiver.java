package ir.firoozeh.unitymodule.Models.Receivers;

import java.io.Serializable;
import java.util.List;

import ir.firoozeh.unitymodule.Models.LeaderBoard;
import ir.firoozeh.unitymodule.Models.Score;
import ir.firoozeh.unitymodule.Models.User;

/**
 * Created by Alireza Ghodrati on 06/10/2018 in
 * Time 12:29 PM for App ir.firoozeh.gameservice.Models.Resivers
 */

public class LeaderBoardReceiver implements Serializable {

    private LeaderBoard leaderboard;
    private List<User> user;
    private List<Score> scores;

    public LeaderBoard getLeaderboard () {
        return leaderboard;
    }

    public void setLeaderboard (LeaderBoard leaderboard) {
        this.leaderboard = leaderboard;
    }

    public List<User> getUser () {
        return user;
    }

    public void setUser (List<User> user) {
        this.user = user;
    }

    public List<Score> getScores () {
        return scores;
    }

    public void setScores (List<Score> scores) {
        this.scores = scores;
    }

    @Override
    public String toString () {
        return "LeaderBoardReceiver{" +
                "leaderboard=" + leaderboard +
                ", user=" + user +
                ", scores=" + scores +
                '}';
    }
}
