package ir.FiroozehCorp.UnityPlugin.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Achievement implements Serializable {


    @SerializedName ("name")
    @Expose
    private String name;

    @SerializedName ("key")
    @Expose
    private String id;

    @SerializedName ("desc")
    @Expose
    private String description;

    @SerializedName ("point")
    @Expose
    private int point;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public int getPoint () {
        return point;
    }

    public void setPoint (int point) {
        this.point = point;
    }
}
