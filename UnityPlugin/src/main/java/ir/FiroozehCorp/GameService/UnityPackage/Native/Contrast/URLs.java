package ir.FiroozehCorp.GameService.UnityPackage.Native.Contrast;

public final class URLs {

    public static final String BaseURL = "https://gamesservice.ir";
    public static final String WSURI = "wss://gamesservice.ir";


    public static final String LoginUser = BaseURL + "/Auth/app/login";
    public static final String Start = BaseURL + "/Auth/start";
    public static final String UserData = BaseURL + "/Api/v1/";


    public static final String DeleteLastSave = BaseURL + "/Api/v1/savegame/delete";
    public static final String SetSavegame = BaseURL + "/Api/v1/savegame/save";
    public static final String GetSavegame = BaseURL + "/Api/v1/savegame";



    public static final String GetAchievements = BaseURL + "/Api/v1/Achievement";
    public static final String EarnAchievement = BaseURL + "/Api/v1/Achievement/unlock/";

    public static final String SubmitScore = BaseURL + "/Api/v1/Leaderboard/submitscore/";
    public static final String GetLeaderboard = BaseURL + "/Api/v1/Leaderboard/";


    public static final String Bucket = BaseURL + "/Api/v1/bucket/";



}
