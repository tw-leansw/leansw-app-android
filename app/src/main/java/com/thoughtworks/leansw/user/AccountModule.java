package com.thoughtworks.leansw.user;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.SaveCallback;

import dagger.Module;
import dagger.Provides;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.internal.operators.OnSubscribeSingle;

@Module
public class AccountModule {

    private final Account account;

    public AccountModule(Account account) {
        this.account = account;
    }

    @AccountScope
    @Provides
    Account provideAccount() {
        return account;
    }

    @AccountScope
    @Provides
    Single<String> providePushInstalliongIdObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (!subscriber.isUnsubscribed()) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                subscriber.onNext(AVInstallation.getCurrentInstallation().getInstallationId());
                            }
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        }).toSingle();
    }
}
