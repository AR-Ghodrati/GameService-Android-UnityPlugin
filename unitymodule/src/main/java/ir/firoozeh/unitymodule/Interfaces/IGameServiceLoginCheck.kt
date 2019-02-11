package ir.firoozeh.unitymodule.Interfaces

/**
 * Created by Alireza Ghodrati on 09/10/2018 in
 * Time 09:58 AM for App ir.firoozeh.unitymodule.Interfaces
 */

interface IGameServiceLoginCheck {
    fun isLoggedIn(Status: Boolean)

    fun OnError(Error: String)
}
