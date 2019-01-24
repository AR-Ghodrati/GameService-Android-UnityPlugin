/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/alirezaghodrati/AndroidStudioProjects/GameService_UnityPlugin/unitymodule/src/main/aidl/ir/firoozeh/gameservice/IAsyncGameServiceCallback.aidl
 */
package ir.firoozeh.gameservice;
// Declare any non-default types here with import statements

public interface IAsyncGameServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ir.firoozeh.gameservice.IAsyncGameServiceCallback
{
private static final java.lang.String DESCRIPTOR = "ir.firoozeh.gameservice.IAsyncGameServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ir.firoozeh.gameservice.IAsyncGameServiceCallback interface,
 * generating a proxy if needed.
 */
public static ir.firoozeh.gameservice.IAsyncGameServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ir.firoozeh.gameservice.IAsyncGameServiceCallback))) {
return ((ir.firoozeh.gameservice.IAsyncGameServiceCallback)iin);
}
return new ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_OnCallback:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.OnCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_OnError:
{
data.enforceInterface(descriptor);
java.lang.String _arg0;
_arg0 = data.readString();
this.OnError(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements ir.firoozeh.gameservice.IAsyncGameServiceCallback
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
@Override public void OnCallback(java.lang.String Result) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Result);
mRemote.transact(Stub.TRANSACTION_OnCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void OnError(java.lang.String Error) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(Error);
mRemote.transact(Stub.TRANSACTION_OnError, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_OnCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_OnError = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public void OnCallback(java.lang.String Result) throws android.os.RemoteException;
public void OnError(java.lang.String Error) throws android.os.RemoteException;
}
