package com.infinitisuite.surveymobile.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URI;
import java.net.URISyntaxException;

public class SurveyWebHttpClient {

    private static AsyncHttpClient client = new AsyncHttpClient();

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
