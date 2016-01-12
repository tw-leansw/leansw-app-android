package com.thoughtworks.leansw.user;

import com.thoughtworks.leansw.ApplicationComponent;

import dagger.Component;
import rx.Single;

@AccountScope
@Component(dependencies = ApplicationComponent.class,
        modules = AccountModule.class)
public interface AccountComponent {
    Account account();

    Single<String> pushInstallationIdObservable();
}
