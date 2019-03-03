package ir.firoozeh.gameservice;

import ir.firoozeh.gameservice.IAsyncGameServiceCallback;

interface IGameServiceInterface {

    void InitService(String ClientId,String ClientSecret,String SysInfo,IAsyncGameServiceCallback callback);
    void RequestVersion(IAsyncGameServiceCallback callback);
    void ShowLeaderBoardUI(IAsyncGameServiceCallback callback);
    void ShowAchievementUI(IAsyncGameServiceCallback callback);
    void RequestAchievement(IAsyncGameServiceCallback callback);
    void RequestLeaderBoards(IAsyncGameServiceCallback callback);
    void RequestLeaderBoardData(String LeaderBoardID,IAsyncGameServiceCallback callback);
    void RequestUnlockAchievement(String Id,boolean HaveNotification,IAsyncGameServiceCallback callback);
    void RequestSubmitScore(String Id, int score,boolean HaveNotification,IAsyncGameServiceCallback callback);
    void RequestGetData(IAsyncGameServiceCallback callback);
    void RequestSaveData(String SaveGameName,String SaveGameDes
    ,String SaveCover,String SaveGameData,IAsyncGameServiceCallback callback);
    void RequestRemoveSaveData(IAsyncGameServiceCallback callback);
    void RequestGetUserData(IAsyncGameServiceCallback callback);



}
