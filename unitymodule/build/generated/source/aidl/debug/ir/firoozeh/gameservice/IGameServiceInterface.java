/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/alirezaghodrati/AndroidStudioProjects/GameService_UnityPlugin/unitymodule/src/main/aidl/ir/firoozeh/gameservice/IGameServiceInterface.aidl
 */
package ir.firoozeh.gameservice;
public interface IGameServiceInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ir.firoozeh.gameservice.IGameServiceInterface
{
private static final java.lang.String DESCRIPTOR = "ir.firoozeh.gameservice.IGameServiceInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ir.firoozeh.gameservice.IGameServiceInterface interface,
 * generating a proxy if needed.
 */
public static ir.firoozeh.gameservice.IGameServiceInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ir.firoozeh.gameservice.IGameServiceInterface))) {
return ((ir.firoozeh.gameservice.IGameServiceInterface)iin);
}
return new ir.firoozeh.gameservice.IGameServiceInterface.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
java.lang.String descriptor = DESCRIPTOR;
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(descriptor);
return true;
}
case TRANSACTION_InitService:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg3;
_arg3 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.InitService(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestVersion:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestVersion(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_ShowLeaderBoardUI:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg1;
_arg1 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.ShowLeaderBoardUI(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_ShowAchievementUI:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.ShowAchievementUI(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestAchievement:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestAchievement(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestLeaderBoards:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestLeaderBoards(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestLeaderBoardData:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg1;
_arg1 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestLeaderBoardData(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestUnlockAchievement:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg2;
_arg2 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestUnlockAchievement(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestSubmitScore:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
boolean _arg2;
_arg2 = (0!=data.readInt());
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg3;
_arg3 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestSubmitScore(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestGetData:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestGetData(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_RequestSaveData:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
java.lang.String _arg3;
_arg3 = data.readString();
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg4;
_arg4 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.RequestSaveData(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements ir.firoozeh.gameservice.IGameServiceInterface
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void InitService(java.lang.String ClientId, java.lang.String ClientSecret, java.lang.String SysInfo, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(ClientId);
_data.writeString(ClientSecret);
_data.writeString(SysInfo);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_InitService, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestVersion(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestVersion, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void ShowLeaderBoardUI(java.lang.String LeaderBoardID, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(LeaderBoardID);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_ShowLeaderBoardUI, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void ShowAchievementUI(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_ShowAchievementUI, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestAchievement(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestAchievement, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestLeaderBoards(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestLeaderBoards, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestLeaderBoardData(java.lang.String LeaderBoardID, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(LeaderBoardID);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestLeaderBoardData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestUnlockAchievement(java.lang.String Id, boolean HaveNotification, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Id);
_data.writeInt(((HaveNotification)?(1):(0)));
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestUnlockAchievement, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestSubmitScore(java.lang.String Id, int score, boolean HaveNotification, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Id);
_data.writeInt(score);
_data.writeInt(((HaveNotification)?(1):(0)));
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestSubmitScore, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestGetData(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestGetData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void RequestSaveData(java.lang.String SaveGameName, java.lang.String SaveGameDes, java.lang.String SaveCover, java.lang.String SaveGameData, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(SaveGameName);
_data.writeString(SaveGameDes);
_data.writeString(SaveCover);
_data.writeString(SaveGameData);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_RequestSaveData, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_InitService = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_RequestVersion = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_ShowLeaderBoardUI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_ShowAchievementUI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_RequestAchievement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_RequestLeaderBoards = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_RequestLeaderBoardData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_RequestUnlockAchievement = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_RequestSubmitScore = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_RequestGetData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_RequestSaveData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
}
public void InitService(java.lang.String ClientId, java.lang.String ClientSecret, java.lang.String SysInfo, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestVersion(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void ShowLeaderBoardUI(java.lang.String LeaderBoardID, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void ShowAchievementUI(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestAchievement(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestLeaderBoards(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestLeaderBoardData(java.lang.String LeaderBoardID, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestUnlockAchievement(java.lang.String Id, boolean HaveNotification, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestSubmitScore(java.lang.String Id, int score, boolean HaveNotification, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestGetData(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
public void RequestSaveData(java.lang.String SaveGameName, java.lang.String SaveGameDes, java.lang.String SaveCover, java.lang.String SaveGameData, ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
}
