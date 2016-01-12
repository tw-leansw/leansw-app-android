package com.thoughtworks.leansw;

import android.app.Application;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.thoughtworks.leansw.user.Account;
import com.thoughtworks.leansw.user.AccountComponent;
import com.thoughtworks.leansw.user.AccountModule;
import com.thoughtworks.leansw.user.DaggerAccountComponent;

public class LeanSWApplication extends Application {

    private ApplicationComponent applicationComponent;
    private AccountComponent accountComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "98tyRw04Fl6neO4GMAw5pz5A-gzGzoHsz", "Ilylwk4p7jqUUU02E2W5omhT");

        AVAnalytics.enableCrashReport(this.getApplicationContext(), true);
        AVOSCloud.setLastModifyEnabled(true);
        AVOSCloud.setDebugLogEnabled(true);
    }

    public ApplicationComponent getApplicationComponent() {
        if (applicationComponent == null)
            applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        return applicationComponent;
    }

    public AccountComponent createUserComponent(Account account) {
        if (accountComponent == null)
            accountComponent = DaggerAccountComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .accountModule(new AccountModule(account))
                    .build();
        return accountComponent;
    }

    public AccountComponent getAccountComponent() {
        return accountComponent;
    }

    public void releaseAccountComponent() {
        accountComponent = null;
    }
}
