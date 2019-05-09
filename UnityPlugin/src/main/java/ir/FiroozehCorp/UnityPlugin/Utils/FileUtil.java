package ir.FiroozehCorp.UnityPlugin.Utils;

public final class FileUtil {

    public static boolean IsSaveFileSizeValid (String SaveGameJSON) {
        return SaveGameJSON.length() <= 128 * 1024;
    }

    public static boolean IsSaveImgFileSizeValid (String SaveCoverBase64) {
        return SaveCoverBase64.length() <= 128 * 1024;
    }
}
