package ir.FiroozehCorp.UnityPlugin.Native.Interfaces;

public interface IGameServiceCallback {
    void OnCallback (String Result);

    void OnError (String Error);
}
