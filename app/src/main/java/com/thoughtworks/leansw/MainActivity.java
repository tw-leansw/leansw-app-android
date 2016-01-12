package com.thoughtworks.leansw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thoughtworks.leansw.user.Account;

import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private PushPermissionsChecker pushPermissionsChecker;
    Account account;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        account = ((LeanSWApplication) getApplication()).getAccountComponent().account();

        pushPermissionsChecker = new PushPermissionsChecker(this);
        if (pushPermissionsChecker.requestPushPermissions()) {
            subscription = enablePushFor(account);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (pushPermissionsChecker.onRequestPermissionsResult(requestCode, grantResults)) {
            subscription = enablePushFor(account);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
            subscription = null;
        }
    }

    Subscription enablePushFor(final Account account) {
        final Single<String> installationIdObservable = ((LeanSWApplication) getApplication()).getAccountComponent().pushInstallationIdObservable();
        return installationIdObservable.subscribe(new SingleSubscriber<String>() {
            @Override
            public void onSuccess(String installationId) {
                if (!installationId.equals(account.installationId)) {
                    // TODO store installation id on server side
                }
            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }

}
