package com.base.utils.common;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.xml.namespace.QName;

/**
 * Created by Administrator on 2015/4/16.
 */
public class CXFUtils {
    public static final int CXF_CLIENT_CONNECT_TIMEOUT = 8 * 1000 * 60;//设置连接超时时间，单位是毫秒
    public static final int CXF_CLIENT_RECEIVE_TIMEOUT = 8 * 1000 * 60;//设置接收数据超时时间

    /**设置cxf的超时时间*/
    public static void configTimeout(Object service) {
        Client proxy = ClientProxy.getClient(service);
        HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(CXF_CLIENT_CONNECT_TIMEOUT);
        policy.setReceiveTimeout(CXF_CLIENT_RECEIVE_TIMEOUT);
        conduit.setClient(policy);
    }

    /**不规则的cxfservice中过滤出ws名称*/
    public static QName filterQName(Endpoint endpoint,String operName){
        QName opName = new QName(endpoint.getService().getName().getNamespaceURI(), operName);
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        if (bindingInfo.getOperation(opName) == null) {
            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                if (operName.equals(operationInfo.getName().getLocalPart())) {
                    opName = operationInfo.getName();
                    break;
                }
            }
        }
        return opName;
    }
}
