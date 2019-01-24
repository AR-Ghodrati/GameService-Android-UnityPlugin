package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 01:47 PM for App ir.firoozeh.unitymodule.Models
 */

public class Platform implements Serializable {

    private String platform;
    private String type;
    private String link;

    public String getPlatform () {
        return platform;
    }

    public void setPlatform (String platform) {
        this.platform = platform;
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
                "platform='" + platform + '\'' +
                ", type='" + type + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
