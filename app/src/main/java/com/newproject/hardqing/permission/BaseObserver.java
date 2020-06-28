package com.newproject.hardqing.permission;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author LLhon
 * @Date 2019/4/28 22:29
 * @description
 */
public abstract class BaseObserver<T> implements Observer<T> {

    public BaseObserver() {

    }

    @Override public void onError(Throwable e) {

    }

    @Override public void onComplete() {

    }

    @Override public void onSubscribe(Disposable d) {

    }
}
