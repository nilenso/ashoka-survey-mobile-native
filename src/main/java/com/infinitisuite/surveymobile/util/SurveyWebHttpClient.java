package com.infinitisuite.surveymobile.util;

import com.infinitisuite.surveymobile.support.TestExecutorService;
import com.infinitisuite.surveymobile.util.net.SurveyWebURI;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SurveyWebHttpClient {
    private AsyncHttpClient client;

    public SurveyWebHttpClient() {
        this.client = new AsyncHttpClient();
    }

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(new SurveyWebURI(url).getAbsoluteUrl(), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(new SurveyWebURI(url).getAbsoluteUrl(), params, responseHandler);
    }
}
