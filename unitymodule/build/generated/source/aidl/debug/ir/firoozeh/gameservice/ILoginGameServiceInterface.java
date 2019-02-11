/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/alirezaghodrati/AndroidStudioProjects/GameService_UnityPlugin/unitymodule/src/main/aidl/ir/firoozeh/gameservice/ILoginGameServiceInterface.aidl
 */
package ir.firoozeh.gameservice;
public interface ILoginGameServiceInterface extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements ir.firoozeh.gameservice.ILoginGameServiceInterface
{
private static final java.lang.String DESCRIPTOR = "ir.firoozeh.gameservice.ILoginGameServiceInterface";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an ir.firoozeh.gameservice.ILoginGameServiceInterface interface,
 * generating a proxy if needed.
 */
public static ir.firoozeh.gameservice.ILoginGameServiceInterface asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof ir.firoozeh.gameservice.ILoginGameServiceInterface))) {
return ((ir.firoozeh.gameservice.ILoginGameServiceInterface)iin);
}
return new ir.firoozeh.gameservice.ILoginGameServiceInterface.Stub.Proxy(obj);
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
case TRANSACTION_isLoggedIn:
{
data.enforceInterface(descriptor);
boolean _result = this.isLoggedIn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_ShowLoginUI:
{
data.enforceInterface(descriptor);
ir.firoozeh.gameservice.IAsyncGameServiceCallback _arg0;
_arg0 = ir.firoozeh.gameservice.IAsyncGameServiceCallback.Stub.asInterface(data.readStrongBinder());
this.ShowLoginUI(_arg0);
reply.writeNoException();
return true;
}
default:
{
return super.onTransact(code, data, reply, flags);
}
}
}
private static class Proxy implements ir.firoozeh.gameservice.ILoginGameServiceInterface
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
@Override public boolean isLoggedIn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isLoggedIn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void ShowLoginUI(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_ShowLoginUI, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_isLoggedIn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_ShowLoginUI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
public boolean isLoggedIn() throws android.os.RemoteException;
public void ShowLoginUI(ir.firoozeh.gameservice.IAsyncGameServiceCallback callback) throws android.os.RemoteException;
}
