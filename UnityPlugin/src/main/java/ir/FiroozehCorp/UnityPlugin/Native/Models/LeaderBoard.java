package ir.FiroozehCorp.UnityPlugin.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaderBoard {

    @SerializedName ("name")
    @Expose
    private String name;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }
}