package ir.FiroozehCorp.GameService.UnityPackage.Utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public final class TextUtil {


    /**
     * ^(?=.{5,20}$)(?![.])(?!.*[.]{2})[a-zA-Z0-9._]+(?<![_])$
     * └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
     * │         │         │            │           no _ or . at the end
     * │         │         │            │
     * │         │         │            allowed characters
     * │         │         │
     * │         │         no __ or _. or ._ or .. inside
     * │         │
     * │         no _ or . at the beginning
     * │
     * username is 5-20 characters long
     */
    private static final String USERNAME_PATTERN = "^(?!.*\\.\\.)(?!.*\\.$)[^\\W][\\w.]{3,50}$";
    /**
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{5,}             # anything, at least eight places though
     * $
     */

    private static final String PASS_PATTERN = "^(?=.*[0-9])(?=\\S+$).{5,}$";

    public static boolean IsEmailValid (String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean IsUserNameValid (String userName) {
        return Pattern.compile(USERNAME_PATTERN).matcher(userName).matches();
    }

    public static boolean IsPassWordValid (String passWord) {
        return Pattern.compile(PASS_PATTERN).matcher(passWord).matches();
    }

    public static boolean IsPhoneValid (String phone) {
        return !phone.isEmpty() && phone.length() == 11;
    }

}
