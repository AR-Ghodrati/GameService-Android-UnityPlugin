package ir.FiroozehCorp.GameService.UnityPackage.Native.Enums;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum TapActionType {
    @SerializedName ("1")
    @Expose
    OPEN_APP,
    @SerializedName ("2")
    @Expose
    OPEN_LINK,
    @SerializedName ("3")
    @Expose
    OPEN_MARKET,
    @SerializedName ("4")
    @Expose
    CLOSE_NOTIFICATION,
    @SerializedName ("5")
    @Expose
    OPEN_EMAIL,
    @SerializedName ("6")
    @Expose
    INVITE_TELEGRAM_CHANNEL,
    @SerializedName ("7")
    @Expose
    OTHER_INTENT
}
