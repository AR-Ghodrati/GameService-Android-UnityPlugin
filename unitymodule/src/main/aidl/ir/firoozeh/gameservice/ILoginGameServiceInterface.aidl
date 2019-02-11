// ILoginGameServiceInterface.aidl
package ir.firoozeh.gameservice;

import ir.firoozeh.gameservice.IAsyncGameServiceCallback;

interface ILoginGameServiceInterface {
   boolean isLoggedIn();
   void ShowLoginUI(IAsyncGameServiceCallback callback);
}
