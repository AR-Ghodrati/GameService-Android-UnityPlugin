package ir.firoozeh.unitymodule.Handlers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.RemoteException
import android.util.Log

import ir.firoozeh.gameservice.IAsyncGameServiceCallback
import ir.firoozeh.gameservice.ILoginGameServiceInterface
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback
import ir.firoozeh.unitymodule.Interfaces.IGameServiceLoginCheck

/**
 * Created by Alireza Ghodrati on 03/10/2018 in
 * Time 11:34 AM for App ir.firoozeh.unitymodule
 */

class UnityLogin {

    private val TAG = javaClass.getName()
    private var loginGameServiceInterface: ILoginGameServiceInterface? = null
    private var loginTestService: LoginService? = null
    private var context: Context? = null
    private var loginCheck: IGameServiceCallback? = null

    init {
        Instance = this
    }

    fun SetUnityContext(activity: Activity) {
        this.context = activity.applicationContext
    }

    fun InitLoginService(callback: IGameServiceCallback) {
        this.loginCheck = callback
        initLoginService(callback)
    }

    fun DisposeService() {
        releaseLoginService()
    }

    private fun initLoginService(callback: IGameServiceCallback) {

        try {
            if (isPackageInstalled("ir.firoozeh.gameservice", context!!.packageManager)) {
                loginTestService = LoginService()
                val i = Intent(
                        "ir.firoozeh.gameservice.Services.LoginService.BIND")
                i.setPackage("ir.firoozeh.gameservice")
                val ret = context?.bindService(i, loginTestService, Context.BIND_AUTO_CREATE)

                if (!ret!!)
                    callback.OnError("GameServiceNotBounded")

            } else
                callback.OnError("GameServiceNotInstalled")
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString())
            callback.OnError("GameServiceException")
        }

    }

    private fun releaseLoginService() {
        context!!.unbindService(loginTestService!!)
        loginTestService = null
        Log.e(TAG, "releaseLoginService(): unbound.")
    }

    fun IsUserLoggedIn(check: IGameServiceLoginCheck) {
        try {
            if (loginGameServiceInterface != null)
                check.isLoggedIn(loginGameServiceInterface?.isLoggedIn!!)
            else
                check.OnError("UnreachableService")
        } catch (e: Exception) {
            check.OnError("GameServiceException")
        }

    }

    fun ShowLoginUI(check: IGameServiceLoginCheck) {
        try {
            if (loginGameServiceInterface != null) {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        check.isLoggedIn(true)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        check.OnError(Error)
                    }
                }
                loginGameServiceInterface?.ShowLoginUI(iAsyncGameServiceCallback)
            } else
                check.OnError("UnreachableService")
        } catch (e: Exception) {
            Log.e(javaClass.name, e.toString())
            check.OnError("GameServiceException")
        }

    }

    private fun isPackageInstalled(packagename: String, packageManager: PackageManager): Boolean {
        try {
            packageManager.getPackageInfo(packagename, 0)
            return true
        } catch (e: Exception) {
            return false
        }

    }

    private inner class LoginService : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, boundService: IBinder) {
            loginGameServiceInterface = ILoginGameServiceInterface.Stub
                    .asInterface(boundService)
            loginCheck?.OnCallback("Success")
            Log.e(TAG, "LoginTestService(): Connected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            loginGameServiceInterface = null
            Log.e(TAG, "LoginTestService(): Disconnected")
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var Instance: UnityLogin? = null

        fun GetInstance(): UnityLogin {
            if (Instance == null) Instance = UnityLogin()
            return Instance as UnityLogin

        }
    }

}
