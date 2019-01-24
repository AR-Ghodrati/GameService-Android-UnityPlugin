// IAsyncGameServiceCallback.aidl
package ir.firoozeh.gameservice;

// Declare any non-default types here with import statements

interface IAsyncGameServiceCallback {
      void OnCallback(String Result);
      void OnError(String Error);
}
