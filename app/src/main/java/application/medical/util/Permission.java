package application.medical.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class Permission {

    private Context context;
    private String[] multiplePermissions;
    private int multiplePermissionsCode;

    public Permission(Context context, String[] multiplePermissions, int multiplePermissionsCode) {
        this.context = context;
        this.multiplePermissions = multiplePermissions;
        this.multiplePermissionsCode = multiplePermissionsCode;
    }

    public boolean checkPermissions() {
        for (String multiplePermission : multiplePermissions) {
            if (ContextCompat.checkSelfPermission(context, multiplePermission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context, multiplePermissions, multiplePermissionsCode);
    }

    public boolean check(int requestCode, int[] grantResults) {
        return requestCode == multiplePermissionsCode && grantResults.length > 0 && resultsCheck(grantResults);
    }

    private boolean resultsCheck(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}