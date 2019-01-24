package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 11:01 AM for App ir.firoozeh.unitymodule.Models
 */

public class Achievement implements Serializable {

    private String name;
    private String key;
    private String desc;
    private int point;
    private String image;
    private boolean status;
    private String game;
    private boolean earned;

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

    public String getDesc () {
        return desc;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public int getPoint () {
        return point;
    }

    public void setPoint (int point) {
        this.point = point;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public boolean isStatus () {
        return status;
    }

    public void setStatus (boolean status) {
        this.status = status;
    }

    public String getGame () {
        return game;
    }

    public void setGame (String game) {
        this.game = game;
    }

    public boolean isEarned () {
        return earned;
    }

    public void setEarned (boolean earned) {
        this.earned = earned;
    }


    @Override
    public String toString () {
        return "Achievement{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                ", point=" + point +
                ", image='" + image + '\'' +
                ", status=" + status +
                ", game='" + game + '\'' +
                ", earned=" + earned +
                '}';
    }
}
