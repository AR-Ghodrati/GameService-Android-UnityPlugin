package ir.FiroozehCorp.GameService.UnityPackage.Native.Enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum MarketType {
    @SerializedName ("1")
    @Expose
    BAZAAR,
    @SerializedName ("2")
    @Expose
    Myket,
    @SerializedName ("3")
    @Expose
    IranApps,
    @SerializedName ("4")
    @Expose
    GooglePlay
}
