package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

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

    @SerializedName ("image")
    @Expose
    private String cover;


    @Override
    public String toString () {
        return "Achievement{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", point=" + point +
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
