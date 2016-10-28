package com.base.utils.cxfclient.postapi.namespace;

import com.base.utils.httpclient.HttpClientUtil;
import org.apache.http.client.HttpClient;
import org.apache.http.message.*;
import org.apache.http.message.BasicHeader;

import java.util.List;

/**
 * Created by Administrator on 2015/3/18.
 * 直接通过本机调用api的情况
 */
public class PostApiByHttpClient {

    public String postApi(List<BasicHeader> basicHeaders,String url,String xml) throws Exception{
        HttpClient httpClient= HttpClientUtil.getHttpsClient();

        String res= HttpClientUtil.post(httpClient,
                url,
                xml,
                "utf-8",
                basicHeaders);
        return res;
    }
}
