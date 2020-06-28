package com.newproject.hardqing.listener;

public interface ICallback<RESULT> {

    void onResult(RESULT result);

    void onError(Throwable error);

}
