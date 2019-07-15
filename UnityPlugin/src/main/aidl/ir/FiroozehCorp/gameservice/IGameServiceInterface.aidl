package ir.FiroozehCorp.GameService;

import ir.FiroozehCorp.GameService.IAsyncGameServiceCallback;

interface IGameServiceInterface {

    void InitService(String ClientId,String ClientSecret,String SysInfo,IAsyncGameServiceCallback callback);
    void RequestVersion(IAsyncGameServiceCallback callback);

    void ShowLeaderBoardUI(IAsyncGameServiceCallback callback);
    void ShowAchievementUI(IAsyncGameServiceCallback callback);
    void ShowGamePageUI(IAsyncGameServiceCallback callback);
    void ShowSurveyUI(IAsyncGameServiceCallback callback);


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


    void RequestDownloadObbDataFile(String ObbDataTAG,IAsyncGameServiceCallback callback);
   //void RequestDownloadBundleDataFile(String BundleDataTAG,IAsyncGameServiceCallback callback);

   void GetAllBucketData(String BucketID, IAsyncGameServiceCallback callback);
   void GetOneBucketData(String BucketID, String ID, IAsyncGameServiceCallback callback);
   void UpdateOneBucketData(String BucketID, String ID,  String BucketJSON, IAsyncGameServiceCallback callback);
   void AddNewBucketData (String BucketID, String BucketJSON, IAsyncGameServiceCallback callback);
   void DeleteOneBucket (String BucketID, String ID, IAsyncGameServiceCallback callback);
   void DeleteAllBucketData(String BucketID, IAsyncGameServiceCallback callback);

}
