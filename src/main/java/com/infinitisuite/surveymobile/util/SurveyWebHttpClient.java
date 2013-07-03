package com.infinitisuite.surveymobile.util;

import com.infinitisuite.surveymobile.support.TestExecutorService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SurveyWebHttpClient {
    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void mock() {
        // Set a custom thread pool so that threaded operations occur synchronously while testing.
        client.setThreadPool(new TestExecutorService(1, 1, 1, TimeUnit.SECONDS, new ArrayBlockingQueue(5)));
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String pathString) {
       URI baseURI = null;
       URI path = null;

       try {
           baseURI = new URI("http://staging.thesurveys.org/");
           path = new URI(pathString);
       } catch (URISyntaxException e) { e.printStackTrace(); }

       return baseURI.resolve(path).toString();
    }
}
