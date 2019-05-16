package ir.FiroozehCorp.UnityPlugin.Utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.DownloadProgressListener;
import ir.FiroozehCorp.UnityPlugin.Native.Interfaces.ImageLoadListener;
import ir.FiroozehCorp.UnityPlugin.Native.Models.OBB;

import static ir.FiroozehCorp.UnityPlugin.Native.Handlers.UnityGameServiceNative.downloadManager;

public final class FileUtil {

    public static boolean IsSaveFileSizeValid (String SaveGameJSON) {
        return SaveGameJSON.length() <= 128 * 1024;
    }

    public static boolean IsSaveImgFileSizeValid (String SaveCoverBase64) {
        return SaveCoverBase64.length() <= 128 * 1024;
    }

    public static boolean IsNeedToDownloadData (Activity activity) {
        File dir = activity.getObbDir();
        OBB obb = NativeUtil.GetOBBMetaData(activity);

        boolean flag = true;

        if (dir != null && obb != null)
            for (File f : dir.listFiles())
                if (f.getName().equals(obb.getName()) && Math.abs(f.length() - obb.getSize()) < 10000) {
                    flag = false;
                    break;
                }

        return flag;
    }

    public static boolean IsFreeSpaceToDownload (Long FileSize) {
        Long Free = Environment.getExternalStorageDirectory().getFreeSpace();
        return Math.abs(FileSize - Free) >= 50000000;// Free Space More 50Mb
    }

    public static String GetObbDir (Activity activity) {
        return activity.getObbDir().getAbsolutePath();
    }

    public static File GetObbFile (Activity activity, String tag) {
        return new File(GetObbDir(activity) + "/" + tag);
    }

    /**
     * Given the size of a file outputs as human readable size using SI prefix.
     * <i>Base 1024</i>
     *
     * @param size Size in bytes of a given File.
     * @return SI String representing the file size (B,KB,MB,GB,TB).
     */
    public static String readableFileSize (long size) {
        if (size <= 0) {
            return "درحال محاسبه...";
        }
        final String[] units = new String[]{"بایت", "کیلوبایت", "مگابایت", "گیگابایت", "ترابایت"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#")
                .format(size / Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    public static void DownloadDataFile (Activity activity, String URL, String tag, final DownloadProgressListener listener) {


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URL));


        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("گیم سرویس");
        request.setDescription("در حال دانلود دیتای بازی...");
        request.setVisibleInDownloadsUi(false);
        request.setDestinationUri(Uri.fromFile(GetObbFile(activity, tag)));

        final long id = downloadManager.enqueue(request);
        new GetDownloadInfo().execute(Pair.create(id, listener));

    }

    private static class GetDownloadInfo
            extends AsyncTask<Pair<Long, DownloadProgressListener>, Void, Void> {

        @Override
        protected Void doInBackground (Pair<Long, DownloadProgressListener>... downloadProgressListeners) {
            while (true) {
                final DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadProgressListeners[0].first);

                Cursor cursor = downloadManager.query(query);
                cursor.moveToFirst();

                int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                    downloadProgressListeners[0].second.onDone();
                    break;
                }
                downloadProgressListeners[0].second.onProgress(bytes_downloaded);
                cursor.close();
            }
            return null;
        }
    }

    public static class LoadImageFromURL
            extends AsyncTask<Pair<String, ImageLoadListener>, Void, Void> {


        @Override
        protected Void doInBackground (Pair<String, ImageLoadListener>... pairs) {
            try {
                URL url = new URL(pairs[0].first);
                pairs[0].second.onLoaded(BitmapFactory.decodeStream(url.openConnection().getInputStream()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pairs[0].second.onLoaded(null);
            return null;
        }
    }

}
