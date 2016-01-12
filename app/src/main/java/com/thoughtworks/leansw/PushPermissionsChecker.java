package com.thoughtworks.leansw;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.thoughtworks.leansw.user.Account;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class PushPermissionsChecker {
    public static final int REQUEST_CODE = 0;
    private final Activity activity;

    public PushPermissionsChecker(Activity activity) {
        this.activity = activity;
    }

    boolean requestPushPermissions() {
        boolean permissionGranted = false;
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Snackbar.make(activity.findViewById(R.id.root), "", Snackbar.LENGTH_INDEFINITE)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                askUserForPermission(activity);
                            }
                        }).show();
            } else {
                askUserForPermission(activity);
            }
        } else {
            permissionGranted = true;
        }
        return permissionGranted;
    }

    boolean onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        boolean permissionResultHandled = false;
        if (requestCode == REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permissionResultHandled = true;
        }
        return permissionResultHandled;
    }



    private void askUserForPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }
}