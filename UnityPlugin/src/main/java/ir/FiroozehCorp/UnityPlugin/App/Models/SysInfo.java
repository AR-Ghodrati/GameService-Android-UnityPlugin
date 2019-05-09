package ir.FiroozehCorp.UnityPlugin.App.Models;

import android.content.res.Configuration;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 16/10/2018 in
 * Time 11:12 AM for App ir.firoozeh.unitymodule.Models
 */

public class SysInfo implements Serializable {

    private String PackageName;
    private String VersionName;
    private int SDKVersion;
    private int VersionCode;
    private String OSAPILevel;
    private String Device;
    private String Model;
    private String Product;
    private String Manufacturer;
    private String OtherTAGS;
    private int ScreenWidth;
    private int ScreenHeight;
    private String SDCardState;
    private int GameOrientation = Configuration.ORIENTATION_LANDSCAPE;
    private String Form;

    public int getGameOrientation () {
        return GameOrientation;
    }

    public String getForm () {
        return Form;
    }

    public void setForm (String form) {
        Form = form;
    }

    public void setGameOrientation (int gameOrientation) {
        GameOrientation = gameOrientation;
    }

    public void setSDKVersion (int SDKVersion) {
        this.SDKVersion = SDKVersion;
    }

    public void setPackageName (String packageName) {
        PackageName = packageName;
    }

    public void setVersionName (String versionName) {
        VersionName = versionName;
    }

    public void setVersionCode (int versionCode) {
        VersionCode = versionCode;
    }

    public void setOSAPILevel (String OSAPILevel) {
        this.OSAPILevel = OSAPILevel;
    }

    public void setDevice (String device) {
        Device = device;
    }

    public void setModel (String model) {
        Model = model;
    }

    public void setProduct (String product) {
        Product = product;
    }

    public void setManufacturer (String manufacturer) {
        Manufacturer = manufacturer;
    }

    public void setOtherTAGS (String otherTAGS) {
        OtherTAGS = otherTAGS;
    }

    public void setScreenWidth (int screenWidth) {
        ScreenWidth = screenWidth;
    }

    public void setScreenHeight (int screenHeight) {
        ScreenHeight = screenHeight;
    }

    public void setSDCardState (String SDCardState) {
        this.SDCardState = SDCardState;
    }

    @Override
    public String toString () {
        return "SysInfo{" +
                "PackageName='" + PackageName + '\'' +
                ", VersionName='" + VersionName + '\'' +
                ", SDKVersion=" + SDKVersion +
                ", VersionCode=" + VersionCode +
                ", OSAPILevel='" + OSAPILevel + '\'' +
                ", Device='" + Device + '\'' +
                ", Model='" + Model + '\'' +
                ", Product='" + Product + '\'' +
                ", Manufacturer='" + Manufacturer + '\'' +
                ", OtherTAGS='" + OtherTAGS + '\'' +
                ", ScreenWidth=" + ScreenWidth +
                ", ScreenHeight=" + ScreenHeight +
                ", SDCardState='" + SDCardState + '\'' +
                ", GameOrientation=" + GameOrientation +
                '}';
    }

    public String ToJSON () {
        return new Gson().toJson(this);
    }

    public String getPackageName () {
        return PackageName;
    }

    public String getVersionName () {
        return VersionName;
    }

    public int getSDKVersion () {
        return SDKVersion;
    }

    public int getVersionCode () {
        return VersionCode;
    }

    public String getOSAPILevel () {
        return OSAPILevel;
    }

    public String getDevice () {
        return Device;
    }

    public String getModel () {
        return Model;
    }

    public String getProduct () {
        return Product;
    }

    public String getManufacturer () {
        return Manufacturer;
    }

    public String getOtherTAGS () {
        return OtherTAGS;
    }

    public int getScreenWidth () {
        return ScreenWidth;
    }

    public int getScreenHeight () {
        return ScreenHeight;
    }

    public String getSDCardState () {
        return SDCardState;
    }
}
