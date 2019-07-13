package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Enums.MarketType;

public class NotificationTapAction implements Serializable {

    @SerializedName ("1")
    @Expose
    private String PackageName;

    @SerializedName ("2")
    @Expose
    private MarketType MarketType;

    @SerializedName ("3")
    @Expose
    private String LinkAddress;

    @SerializedName ("4")
    @Expose
    private String EmailAddress;

    @SerializedName ("5")
    @Expose
    private String EmailTitle;

    @SerializedName ("6")
    @Expose
    private String EmailBody;

    @SerializedName ("7")
    @Expose
    private String TelegramChennel;

    @SerializedName ("8")
    @Expose
    private String IntentAction;

    public String getPackageName () {
        return PackageName;
    }

    public ir.FiroozehCorp.GameService.UnityPackage.Native.Enums.MarketType getMarketType () {
        return MarketType;
    }

    public String getLinkAddress () {
        return LinkAddress;
    }

    public String getEmailAddress () {
        return EmailAddress;
    }

    public String getEmailTitle () {
        return EmailTitle;
    }

    public String getEmailBody () {
        return EmailBody;
    }

    public String getTelegramChennel () {
        return TelegramChennel;
    }

    public String getIntentAction () {
        return IntentAction;
    }

    @Override
    public String toString () {
        return "NotificationTapAction{" +
                "PackageName='" + PackageName + '\'' +
                ", MarketType='" + MarketType + '\'' +
                ", LinkAddress='" + LinkAddress + '\'' +
                ", EmailAddress='" + EmailAddress + '\'' +
                ", EmailTitle='" + EmailTitle + '\'' +
                ", EmailBody='" + EmailBody + '\'' +
                ", TelegramChennel='" + TelegramChennel + '\'' +
                ", IntentAction='" + IntentAction + '\'' +
                '}';
    }
}
