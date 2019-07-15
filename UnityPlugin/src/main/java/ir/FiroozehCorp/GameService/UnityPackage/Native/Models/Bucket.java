package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Bucket implements Serializable {

    @SerializedName ("bucket")
    @Expose
    private String BucketID;

    @SerializedName ("_id")
    @Expose
    private String id;

    @SerializedName ("data")
    @Expose
    private String data;

    public String getBucketID () {
        return BucketID;
    }

    public void setBucketID (String bucketID) {
        BucketID = bucketID;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getData () {
        return data;
    }

    public void setData (String data) {
        this.data = data;
    }

    @Override
    public String toString () {
        return "Bucket{" +
                "BucketID='" + BucketID + '\'' +
                ", id='" + id + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
