package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

class Platform implements Serializable {

    @SerializedName ("os")
    @Expose
    private String os;

    @SerializedName ("type")
    @Expose
    private String type;

    @SerializedName ("link")
    @Expose
    private String link;

    public String getOs () {
        return os;
    }

    public void setOs (String os) {
        this.os = os;
    }

    public String getType () {
        return type;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
    }

    @Override
    public String toString () {
        return "Platform{" +
                "os='" + os + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
