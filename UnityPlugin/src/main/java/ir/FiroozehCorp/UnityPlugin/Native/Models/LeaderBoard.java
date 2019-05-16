package ir.FiroozehCorp.UnityPlugin.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoard {

    @SerializedName ("name")
    @Expose
    private String name;

    @SerializedName ("image")
    @Expose
    private String cover;


    @Override
    public String toString () {
        return "LeaderBoard{" +
                "name='" + name + '\'' +
                ", cover='" + cover + '\'' +
                '}';
    }

    public String getCover () {
        return cover;
    }

    public void setCover (String cover) {
        this.cover = cover;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}
