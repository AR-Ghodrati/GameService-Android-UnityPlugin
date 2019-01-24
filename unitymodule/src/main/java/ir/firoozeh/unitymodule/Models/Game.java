package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 01:42 PM for App ir.firoozeh.unitymodule.Models
 */

public class Game implements Serializable {

    private String name;
    private String category;
    private int installed;
    private String explane;
    private String id;
    private String secret;
    private int created;
    private String link;
    private int status;
    private String logo;
    private String publisher;
    private List<String> picture;
    private List<Media> media;
    private List<Platform> platform;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getCategory () {
        return category;
    }

    public void setCategory (String category) {
        this.category = category;
    }

    public int getInstalled () {
        return installed;
    }

    public void setInstalled (int installed) {
        this.installed = installed;
    }

    public String getExplane () {
        return explane;
    }

    public void setExplane (String explane) {
        this.explane = explane;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getSecret () {
        return secret;
    }

    public void setSecret (String secret) {
        this.secret = secret;
    }

    public int getCreated () {
        return created;
    }

    public void setCreated (int created) {
        this.created = created;
    }

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public String getLogo () {
        return logo;
    }

    public void setLogo (String logo) {
        this.logo = logo;
    }

    public String getPublisher () {
        return publisher;
    }

    public void setPublisher (String publisher) {
        this.publisher = publisher;
    }

    public List<String> getPicture () {
        return picture;
    }

    public void setPicture (List<String> picture) {
        this.picture = picture;
    }

    public List<Media> getMedia () {
        return media;
    }

    public void setMedia (List<Media> media) {
        this.media = media;
    }

    public List<Platform> getPlatform () {
        return platform;
    }

    public void setPlatform (List<Platform> platform) {
        this.platform = platform;
    }

    @Override
    public String toString () {
        return "Game{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", installed=" + installed +
                ", explane='" + explane + '\'' +
                ", id='" + id + '\'' +
                ", secret='" + secret + '\'' +
                ", created=" + created +
                ", link='" + link + '\'' +
                ", status=" + status +
                ", logo='" + logo + '\'' +
                ", publisher='" + publisher + '\'' +
                ", picture=" + picture +
                ", media=" + media +
                ", platform=" + platform +
                '}';
    }
}
