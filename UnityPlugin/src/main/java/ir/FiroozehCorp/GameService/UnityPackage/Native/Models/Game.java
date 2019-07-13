package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {

    @SerializedName ("id")
    @Expose
    private String id;


    @SerializedName ("_id")
    @Expose
    private String _id;


    @SerializedName ("name")
    @Expose
    private String name;

    @SerializedName ("package")
    @Expose
    private String Package;

    @SerializedName ("category")
    @Expose
    private String category;

    @SerializedName ("installed")
    @Expose
    private int installed;

    @SerializedName ("explane")
    @Expose
    private String explane;

    @SerializedName ("created")
    @Expose
    private int created;

    @SerializedName ("link")
    @Expose
    private String link;

    @SerializedName ("status")
    @Expose
    private int status;

    @SerializedName ("logo")
    @Expose
    private String logoURL;


    @SerializedName ("cover")
    @Expose
    private String coverURL;


    @SerializedName ("publisher")
    @Expose
    private Publisher publisher;

    @SerializedName ("picture")
    @Expose
    private List<String> pictures;


    @SerializedName ("platform")
    @Expose
    private List<Platform> platforms;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getPackage () {
        return Package;
    }

    public void setPackage (String aPackage) {
        Package = aPackage;
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

    public String getLogoURL () {
        return logoURL;
    }

    public void setLogoURL (String logoURL) {
        this.logoURL = logoURL;
    }

    public String getCoverURL () {
        return coverURL;
    }

    public void setCoverURL (String coverURL) {
        this.coverURL = coverURL;
    }

    public Publisher getPublisher () {
        return publisher;
    }

    public void setPublisher (Publisher publisher) {
        this.publisher = publisher;
    }

    public List<String> getPictures () {
        return pictures;
    }

    public void setPictures (List<String> pictures) {
        this.pictures = pictures;
    }

    public List<Platform> getPlatforms () {
        return platforms;
    }

    public void setPlatforms (List<Platform> platforms) {
        this.platforms = platforms;
    }

    public String get_id () {
        return _id;
    }

    public void set_id (String _id) {
        this._id = _id;
    }

    @Override
    public String toString () {
        return "Game{" +
                "id='" + id + '\'' +
                ", _id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", Package='" + Package + '\'' +
                ", category='" + category + '\'' +
                ", installed=" + installed +
                ", explane='" + explane + '\'' +
                ", created=" + created +
                ", link='" + link + '\'' +
                ", status=" + status +
                ", logoURL='" + logoURL + '\'' +
                ", coverURL='" + coverURL + '\'' +
                ", publisher=" + publisher +
                ", pictures=" + pictures +
                ", platforms=" + platforms +
                '}';
    }
}
