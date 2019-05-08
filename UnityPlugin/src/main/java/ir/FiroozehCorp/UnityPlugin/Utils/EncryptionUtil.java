package ir.FiroozehCorp.UnityPlugin.Utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class EncryptionUtil {

    public static SecretKey generateKey (String Key) {
        return new SecretKeySpec(Key.getBytes(), "AES");
    }

    public static byte[] encryptString (String message, SecretKey secret)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        /* Encrypt the message. */
        Cipher cipher;
        cipher = Cipher.getInstance("AES/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        return cipher.doFinal(message.getBytes());
    }

    public static String decryptString (byte[] cipherText, SecretKey secret)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        Cipher cipher;
        cipher = Cipher.getInstance("AES/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }
}
