package ir.FiroozehCorp.GameService;

import ir.FiroozehCorp.GameService.IAsyncGameServiceCallback;

interface ILoginGameServiceInterface {
   boolean isLoggedIn();
   void ShowLoginUI(IAsyncGameServiceCallback callback);
}
