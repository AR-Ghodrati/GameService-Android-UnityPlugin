package ir.FiroozehCorp.GameService.UnityPackage.App.Interfaces;

/**
 * Created by Alireza Ghodrati on 09/10/2018 in
 * Time 09:58 AM for App ir.firoozeh.unitymodule.Interfaces
 */

public interface IGameServiceLoginCheck {
    void isLoggedIn (boolean Status);
    void OnError (String Error);
}
