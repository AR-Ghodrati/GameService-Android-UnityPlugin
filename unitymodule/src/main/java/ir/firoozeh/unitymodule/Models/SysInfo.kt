package ir.firoozeh.unitymodule.Models

import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * Created by Alireza Ghodrati on 16/10/2018 in
 * Time 11:12 AM for App ir.firoozeh.unitymodule.Models
 */

class SysInfo : Serializable {

    @SerializedName("PackageName")
    @Expose
    var PackageName: String? = null

    @SerializedName("VersionName")
    @Expose
    var VersionName: String? = null

    @SerializedName("SDKVersion")
    @Expose
    var SDKVersion: Int = 0

    @SerializedName("VersionCode")
    @Expose
    var VersionCode: Int = 0

    @SerializedName("OSAPILevel")
    @Expose
    var OSAPILevel: String? = null

    @SerializedName("Device")
    @Expose
    var Device: String? = null

    @SerializedName("Model")
    @Expose
    var Model: String? = null

    @SerializedName("Product")
    @Expose
    var Product: String? = null

    @SerializedName("Manufacturer")
    @Expose
    var Manufacturer: String? = null

    @SerializedName("OtherTAGS")
    @Expose
    var OtherTAGS: String? = null

    @SerializedName("ScreenWidth")
    @Expose
    var ScreenWidth: Int = 0

    @SerializedName("ScreenHeight")
    @Expose
    var ScreenHeight: Int = 0


    @SerializedName("SDCardState")
    @Expose
    var SDCardState: String? = null


    fun ToJSON(): String {
        return Gson().toJson(this)
    }

    override fun toString(): String {
        return "SysInfo(PackageName=$PackageName, VersionName=$VersionName, SDKVersion=$SDKVersion, VersionCode=$VersionCode, OSAPILevel=$OSAPILevel, Device=$Device, Model=$Model, Product=$Product, Manufacturer=$Manufacturer, OtherTAGS=$OtherTAGS, ScreenWidth=$ScreenWidth, ScreenHeight=$ScreenHeight, SDCardState=$SDCardState)"
    }
}
