package com.guet.shareapp.Utils;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {
    private Map<HttpUrl, List<Cookie>> cookieStore=new HashMap<>();


    private HttpUrl url; //上一个请求

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        List<Cookie> cookies = cookieStore.get(url);

        return null != cookies ? cookies:new ArrayList<>();
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        cookieStore.put(httpUrl,list);
        url=httpUrl;
    }
}
