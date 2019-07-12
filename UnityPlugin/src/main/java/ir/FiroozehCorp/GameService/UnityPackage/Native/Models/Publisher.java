package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Publisher implements Serializable {

    @SerializedName ("_id")
    @Expose
    private String id;

    @SerializedName ("name")
    @Expose
    private String name;

    @SerializedName ("logo")
    @Expose
    private String logoURL;

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getLogoURL () {
        return logoURL;
    }

    public void setLogoURL (String logoURL) {
        this.logoURL = logoURL;
    }

    @Override
    public String toString () {
        return "Publisher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", logoURL='" + logoURL + '\'' +
                '}';
    }
}
