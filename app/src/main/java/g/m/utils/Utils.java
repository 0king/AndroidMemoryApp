package g.m.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.compat.BuildConfig;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by kushroxx on 27/11/16.
 */

public class Utils {

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, 1).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static String getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }

    public static int[] generateShuffledListOfIds(int count) {
        int[] ids = new int[count];
        for (int i = 0; i < count; i++) {
            ids[i] = i;
        }
        shuffle(ids);
        return ids;
    }

    public static void shuffle(int[] array) {
        Random random = new Random();
        for (int i = array.length; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
