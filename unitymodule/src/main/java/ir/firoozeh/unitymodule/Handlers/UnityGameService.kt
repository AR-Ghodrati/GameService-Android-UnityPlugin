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
import ir.firoozeh.gameservice.IGameServiceInterface
import ir.firoozeh.unitymodule.Interfaces.IGameServiceCallback
import ir.firoozeh.unitymodule.Utils.DeviceInformationUtil

/**
 * Created by Alireza Ghodrati on 04/10/2018 in
 * Time 02:36 PM for App ir.firoozeh.unitymodule
 */

class UnityGameService {

    private val TAG = javaClass.getName()
    private var gameServiceInterface: IGameServiceInterface? = null
    private var gameService: GameService? = null
    private var context: Context? = null
    private var UnityActivity: Activity? = null

    private var clientId: String? = null
    private var clientSecret: String? = null
    private var IsServiceRunning = false
    private var InitCallback: IGameServiceCallback? = null

    init {
        Instance = this
    }

    fun SetUnityContext(activity: Activity) {
        this.context = activity.applicationContext
        this.UnityActivity = activity
    }

    fun InitGameService(clientId: String?, clientSecret: String?, callback: IGameServiceCallback) {
        if (clientId != null && clientSecret != null
                && clientId.isNotEmpty() && clientSecret.isNotEmpty()) {
            this.clientId = clientId
            this.clientSecret = clientSecret
            this.InitCallback = callback
            initGameService(InitCallback)
        } else
            callback.OnError("InvalidInputs")
    }

    fun DisposeService() {
        releaseGameService()
    }

    private fun releaseGameService() {
        this.context?.unbindService(gameService)
        gameService = null
        Log.d(TAG, "releaseGameService(): unbound.")
    }

    private fun initGameService(callback: IGameServiceCallback?) {

        try {
            if (isPackageInstalled(context?.packageManager!!)) {

                Log.e(TAG, "initGameService()")

                gameService = GameService()

                val i = Intent(
                        "ir.firoozeh.gameservice.Services.GameService.BIND")
                i.setPackage("ir.firoozeh.gameservice")

                val ret = context?.bindService(i, gameService, Context.BIND_AUTO_CREATE)
                if (!ret!!)
                    callback!!.OnError("GameServiceNotBounded")
            } else
                callback!!.OnError("GameServiceNotInstalled")
        } catch (e: Exception) {
            Log.e(TAG, "initGameService() Exception:$e")
            callback!!.OnError("GameServiceException")
        }

    }

    fun GetAchievement(callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestAchievement(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun GetLeaderBoards(callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestLeaderBoards(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun GetLeaderBoardData(LeaderBoardID: String, callback: IGameServiceCallback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestLeaderBoardData(LeaderBoardID, iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun ShowLeaderBoardUI(callback: IGameServiceCallback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {

                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.ShowLeaderBoardUI(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun ShowAchievementUI(callback: IGameServiceCallback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {

                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.ShowAchievementUI(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun UnlockAchievement(AchievementID: String, HaveNotification: Boolean, callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestUnlockAchievement(AchievementID, HaveNotification, iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }


    fun SubmitScore(Id: String, Score: Int, HaveNotification: Boolean, callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestSubmitScore(Id, Score, HaveNotification, iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun GetLastSave(callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        callback.OnCallback(Result)
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestGetData(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun SaveData(Name: String, Description: String, Cover: String, SaveData: String, callback: IGameServiceCallback) {

        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {

                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestSaveData(Name, Description, Cover, SaveData, iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    fun SYSTEM_INFO(callback: IGameServiceCallback) {
        try {
            if (gameServiceInterface == null) {
                callback.OnError("UnreachableService")
            } else {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        val sysInfo = DeviceInformationUtil.GetSystemInfo(UnityActivity!!)
                        sysInfo.SDKVersion = Integer.parseInt(Result)
                        callback.OnCallback(sysInfo.ToJSON())
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        callback.OnError(Error)
                    }
                }
                gameServiceInterface?.RequestVersion(iAsyncGameServiceCallback)
            }
        } catch (e: Exception) {
            callback.OnError("Exception:$e")
        }

    }

    private fun isPackageInstalled(packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo("ir.firoozeh.gameservice", 0)
            true
        } catch (e: Exception) {
            false
        }

    }

    private inner class GameService : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, boundService: IBinder) {
            gameServiceInterface = IGameServiceInterface.Stub
                    .asInterface(boundService)
            try {
                val iAsyncGameServiceCallback = object : IAsyncGameServiceCallback.Stub() {
                    @Throws(RemoteException::class)
                    override fun OnCallback(Result: String) {
                        IsServiceRunning = true
                        InitCallback!!.OnCallback("Success")
                    }

                    @Throws(RemoteException::class)
                    override fun OnError(Error: String) {
                        IsServiceRunning = false
                        InitCallback!!.OnError(Error)
                    }
                }
                gameServiceInterface?.InitService(clientId, clientSecret, DeviceInformationUtil.GetSystemInfo(UnityActivity!!).ToJSON(), iAsyncGameServiceCallback)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

            Log.e(TAG, "GameService:Connected")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            IsServiceRunning = false
            gameServiceInterface = null
            Log.e(TAG, "GameServiceTest(): Disconnected")
        }
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var Instance: UnityGameService? = null

        fun GetInstance(): UnityGameService {
            if (Instance == null) {
                Instance = UnityGameService()
            }
            return Instance as UnityGameService
        }
    }
}
