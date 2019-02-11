package ir.firoozeh.unitymodule.Interfaces

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 03:56 PM for App ir.firoozeh.unitymodule.Interfaces
 */

interface IGameServiceCallback {
    fun OnCallback(Result: String)

    fun OnError(Error: String)
}
