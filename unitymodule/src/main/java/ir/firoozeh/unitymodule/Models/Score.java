package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 06/10/2018 in
 * Time 12:32 PM for App ir.firoozeh.gameservice.Models
 */

public class Score implements Serializable {

    private String game;
    private String user;
    private int value;
    private int time;
    private String leaderboard;

    public String getGame () {
        return game;
    }

    public void setGame (String game) {
        this.game = game;
    }

    public String getUser () {
        return user;
    }

    public void setUser (String user) {
        this.user = user;
    }

    public int getValvalueue () {
        return value;
    }

    public void setValvalueue (int valvalueue) {
        value = valvalueue;
    }

    public int getTime () {
        return time;
    }

    public void setTime (int time) {
        this.time = time;
    }

    public String getLeaderboard () {
        return leaderboard;
    }

    public void setLeaderboard (String leaderboard) {
        this.leaderboard = leaderboard;
    }

    @Override
    public String toString () {
        return "Score{" +
                "game='" + game + '\'' +
                ", user='" + user + '\'' +
                ", Valvalueue=" + value +
                ", time=" + time +
                ", leaderboard='" + leaderboard + '\'' +
                '}';
    }
}
