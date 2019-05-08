package ir.FiroozehCorp.UnityPlugin.Native.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.FiroozehCorp.UnityPlugin.R;
import ir.FiroozehCorp.UnityPlugin.Utils.TextUtil;

public class LoginDialog extends Dialog {


    private boolean IsLoginMod = true;
    private boolean CanDissmised = true;
    private boolean CheckData;

    private int Page = 0;
    private Drawable background;


    private Button login, register;
    private EditText Email, UserName, Password;
    private TextView Description;


    public LoginDialog (Context context) {
        super(context);
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        setContentView(R.layout.login_dialog);

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
        if (window != null)
            window.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);


        super.onCreate(savedInstanceState);
    }

    private void RegisterUser () {

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

        }
    }


}
