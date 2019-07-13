package ir.FiroozehCorp.GameService.UnityPackage.Native.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ir.FiroozehCorp.GameService.UnityPackage.Native.Enums.TapActionType;

public class Notification implements Serializable {

    @SerializedName ("1")
    @Expose
    private String title;

    @SerializedName ("2")
    @Expose
    private String Description;

    @SerializedName ("3")
    @Expose
    private String Icon;

    @SerializedName ("4")
    @Expose
    private TapActionType TapActionType = ir.FiroozehCorp.GameService.UnityPackage.Native.Enums.TapActionType.CLOSE_NOTIFICATION;

    @SerializedName ("5")
    @Expose
    private NotificationTapAction TapAction;

    @SerializedName ("6")
    @Expose
    private String JsonData;

    public String getTitle () {
        return title;
    }

    public String getDescription () {
        return Description;
    }

    public String getIcon () {
        return Icon;
    }

    public ir.FiroozehCorp.GameService.UnityPackage.Native.Enums.TapActionType getTapActionType () {
        return TapActionType;
    }

    public NotificationTapAction getTapAction () {
        return TapAction;
    }

    public String getJsonData () {
        return JsonData;
    }

    public boolean IsOnlyJson () {
        return getTitle() == null && getDescription() == null;
    }

    public boolean HaveJsonData () {
        return getJsonData() != null;
    }

    @Override
    public String toString () {
        return "Notification{" +
                "title='" + title + '\'' +
                ", Description='" + Description + '\'' +
                ", Icon='" + Icon + '\'' +
                ", TapActionType=" + TapActionType +
                ", TapAction=" + TapAction +
                ", JsonData='" + JsonData + '\'' +
                '}';
    }
}
