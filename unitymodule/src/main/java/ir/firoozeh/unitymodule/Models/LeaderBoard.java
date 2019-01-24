package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 11:13 AM for App ir.firoozeh.unitymodule.Models
 */

public class LeaderBoard implements Serializable {

    private String name;
    private String key;
    private boolean status;
    private String image;
    private int from;
    private int to;
    private int order;
    private String game;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
    }

    public boolean isStatus () {
        return status;
    }

    public void setStatus (boolean status) {
        this.status = status;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public int getFrom () {
        return from;
    }

    public void setFrom (int from) {
        this.from = from;
    }

    public int getTo () {
        return to;
    }

    public void setTo (int to) {
        this.to = to;
    }

    public int getOrder () {
        return order;
    }

    public void setOrder (int order) {
        this.order = order;
    }

    public String getGame () {
        return game;
    }

    public void setGame (String game) {
        this.game = game;
    }

    @Override
    public String toString () {
        return "LeaderBoard{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", status=" + status +
                ", image='" + image + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", order=" + order +
                ", game='" + game + '\'' +
                '}';
    }
}
