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
    private String ID;

    @SerializedName ("data")
    @Expose
    private String Data;

    public String getBucketID () {
        return BucketID;
    }

    public String getID () {
        return ID;
    }

    public String getData () {
        return Data;
    }

    public void setBucketID (String bucketID) {
        BucketID = bucketID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public void setData (String data) {
        Data = data;
    }

    @Override
    public String toString () {
        return "Bucket{" +
                "BucketID='" + BucketID + '\'' +
                ", ID='" + ID + '\'' +
                ", Data='" + Data + '\'' +
                '}';
    }
}
