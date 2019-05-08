package ir.FiroozehCorp.UnityPlugin.Native.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ir.FiroozehCorp.UnityPlugin.Native.Contrast.ErrorList;
import ir.FiroozehCorp.UnityPlugin.Native.Handlers.UnityGameServiceNative;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.JsonObjectCallbackListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.LoginListener;
import ir.FiroozehCorp.UnityPlugin.R;
import ir.FiroozehCorp.UnityPlugin.Utils.ApiRequestUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.ConnectivityUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.NativeUtil;
import ir.FiroozehCorp.UnityPlugin.Utils.TextUtil;

public class LoginDialog extends Dialog {


    private LoginListener listener;
    private boolean IsLoginMod = true;
    private boolean CanDissmised = true;
    private boolean CheckData;


    private Button login, register;
    private EditText Email, UserName, Password;
    private TextView Description;

    public void setListener (LoginListener listener) {
        this.listener = listener;
    }


    public LoginDialog (Context context) {
        super(context);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setContentView(R.layout.login_dialog);
        setCancelable(false);


        login = findViewById(R.id.buttonLogin);
        register = findViewById(R.id.register);

        Email = findViewById(R.id.editTextEmail);
        UserName = findViewById(R.id.editTextUserName);
        Password = findViewById(R.id.editTextPassword);

        Description = findViewById(R.id.des);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (!CheckData) {
                    IsLoginMod = false;

                    Email.setVisibility(View.VISIBLE);
                    UserName.setVisibility(View.GONE);
                    Password.setVisibility(View.VISIBLE);
                    register.setVisibility(View.GONE);

                    Description.setText("به حساب کاربری خود وارد شوید");
                    CheckData = true;
                } else LoginUser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if (!CheckData) {
                    IsLoginMod = false;

                    Email.setVisibility(View.VISIBLE);
                    UserName.setVisibility(View.VISIBLE);
                    Password.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);

                    Description.setText("ثبت نام کنید");
                    CheckData = true;
                } else RegisterUser();
            }
        });


        int width;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.9);
        else
            width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.6);

        Window window = getWindow();
        if (window != null) {
            window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        }


        super.onCreate(savedInstanceState);
    }

    private void RegisterUser () {
        String email = Email.getText().toString().trim();
        String pass = Password.getText().toString().trim();
        String username = UserName.getText().toString().trim();

        if (username.isEmpty()) {
            UserName.setError("نام کاربری مورد نظر خود را وارد کنید");
            UserName.requestFocus();
        } else if (!TextUtil.IsUserNameValid(username)) {
            UserName.setError("نام کاربری معتبر وارد کنید");
            UserName.requestFocus();
        } else if (email.isEmpty()) {
            Email.setError("ایمیل خود را وارد کنید");
            Email.requestFocus();
        } else if (!TextUtil.IsEmailValid(email)) {
            Email.setError("یک ایمیل معتبر وارد کنید");
            Email.requestFocus();
        } else if (pass.isEmpty()) {
            Password.setError("رمز عبور خود را وارد کنید");
            Password.requestFocus();
        } else if (!TextUtil.IsPassWordValid(pass)) {
            Password.setError("رمز عبور معتبر وارد کنید \n (رمز عبور باید حداقل 5 کارکتر باشد)");
            Password.requestFocus();
        } else {

            if (ConnectivityUtil.isNetworkConnected(getContext())) {
                CanDissmised = false;
                ApiRequestUtil.registerUser(getContext(), username, email, pass, new JsonObjectCallbackListener() {
                    @Override
                    public void onResponse (JSONObject object) {
                        CanDissmised = true;
                        try {
                            if (object.getBoolean("status")) {
                                UnityGameServiceNative.StartTime = System.currentTimeMillis();
                                NativeUtil.SetPlayToken(getContext(), object.getString("token"));
                                Toast.makeText(getContext(), "حساب کاربری شما با موفقیت ساخته شد", Toast.LENGTH_LONG).show();
                                listener.onFinish();
                                dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError (String error) {

                        if (UnityGameServiceNative.IsLogEnable)
                            Log.e(getClass().getName(), error);

                        switch (error) {
                            case ErrorList.WrongPassword:
                                Password.setError("رمز وارد شده درست نمی باشد");
                                Password.requestFocus();
                                break;
                            case ErrorList.EmailExist:
                                Email.setError("این ایمیل وجود دارد");
                                Email.requestFocus();
                                break;
                            case ErrorList.UserNameExist:
                                UserName.setError("این نام مستعار وجود دارد");
                                UserName.requestFocus();
                                break;
                            case ErrorList.InvalidInput:
                                Toast.makeText(getContext(), "خطایی رخ داد،ورودی های خود را بررسی کنید", Toast.LENGTH_LONG).show();
                                break;
                        }

                        listener.onError(error);
                    }
                });
            } else
                Toast.makeText(getContext(), "اتصال با اینترنت برقرار نیست", Toast.LENGTH_SHORT).show();

        }
    }

    private void LoginUser () {
        String email = Email.getText().toString().trim();
        String pass = Password.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("ایمیل خود را وارد کنید");
            Email.requestFocus();
        } else if (!TextUtil.IsEmailValid(email)) {
            Email.setError("یک ایمیل معتبر وارد کنید");
            Email.requestFocus();
        } else if (pass.isEmpty()) {
            Password.setError("رمز عبور خود را وارد کنید");
            Password.requestFocus();
        } else if (!TextUtil.IsPassWordValid(pass)) {
            Password.setError("رمز عبور معتبر وارد کنید \n (رمز عبور باید حداقل 5 کارکتر باشد)");
            Password.requestFocus();
        } else {

            if (ConnectivityUtil.isNetworkConnected(getContext())) {
                CanDissmised = false;
                ApiRequestUtil.loginUser(getContext(), email, pass, new JsonObjectCallbackListener() {
                    @Override
                    public void onResponse (JSONObject object) {
                        CanDissmised = true;
                        try {
                            if (object.getBoolean("status")) {
                                UnityGameServiceNative.StartTime = System.currentTimeMillis();
                                NativeUtil.SetPlayToken(getContext(), object.getString("token"));
                                Toast.makeText(getContext(), "وارد شدید!", Toast.LENGTH_LONG).show();
                                listener.onFinish();
                                dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError (String error) {
                        CanDissmised = true;
                        if (UnityGameServiceNative.IsLogEnable)
                            Log.e(getClass().getName(), error);


                        switch (error) {
                            case ErrorList.WrongPassword:
                                Password.setError("رمز وارد شده درست نمی باشد");
                                Password.requestFocus();
                                break;
                            case ErrorList.EmailNotFound:
                                Email.setError("این ایمیل وجود ندارد");
                                Email.requestFocus();
                                break;
                            case ErrorList.InvalidInput:
                                Toast.makeText(getContext(), "خطایی رخ داد،ورودی های خود را بررسی کنید", Toast.LENGTH_LONG).show();
                                break;
                        }

                        listener.onError(error);
                    }
                });
            } else
                Toast.makeText(getContext(), "اتصال با اینترنت برقرار نیست", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed () {

        if (CheckData) {
            CheckData = false;

            Email.post(new Runnable() {
                @Override
                public void run () {
                    Email.setError(null);
                    Email.setText(null);
                    Email.setVisibility(View.GONE);
                }
            });

            Password.post(new Runnable() {
                @Override
                public void run () {
                    Password.setError(null);
                    Password.setText(null);
                    Password.setVisibility(View.GONE);
                }
            });

            UserName.post(new Runnable() {
                @Override
                public void run () {
                    UserName.setError(null);
                    UserName.setText(null);
                    UserName.setVisibility(View.GONE);
                }
            });

            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.VISIBLE);
            Description.setText("به حساب کاربری خود وارد شوید یا ثبت نام کنید");
        } else {
            if (CanDissmised) {
                listener.onDismiss();
                dismiss();
                super.onBackPressed();
            }
        }
    }
}
