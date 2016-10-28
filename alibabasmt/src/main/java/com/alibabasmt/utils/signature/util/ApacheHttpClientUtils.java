package com.alibabasmt.utils.signature.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

/**
 * Created by Administrator on 2015/3/19.
 */
public class ApacheHttpClientUtils {
    private static MultiThreadedHttpConnectionManager connectionManager =new MultiThreadedHttpConnectionManager();
    static {
        HttpConnectionManagerParams httpParams=new HttpConnectionManagerParams();
        httpParams.setDefaultMaxConnectionsPerHost(20);
        httpParams.setMaxTotalConnections(30);
        httpParams.setConnectionTimeout(120 * 1000);
        httpParams.setSoTimeout(120 * 1000);
        connectionManager.setParams(httpParams);
    }
    /**获取一个https*/
    public static HttpClient getNewHttpsClient(){
        Protocol protocol=new Protocol("https", new MySecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", protocol);

        final HttpClient httpClient = new HttpClient(connectionManager);
        /*httpClient.getHttpConnectionManager()
                .getConnection(httpClient.getHostConfiguration())
                .setProtocol(protocol);*/
        return httpClient;

    }


    public static HttpClient getNewHttpClient(){
        HttpClient httpClient = new HttpClient(connectionManager);
        return httpClient;
    }

}
