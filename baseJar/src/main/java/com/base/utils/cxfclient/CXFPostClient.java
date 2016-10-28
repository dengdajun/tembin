package com.base.utils.cxfclient;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.CXFUtils;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.cxfclient.postapi.namespace.PostApiByHttpClient;
import com.base.utils.cxfclient.postapi.namespace.PostApiService;
import com.base.utils.httpclient.ApiHeader;
import com.base.utils.imageManage.service.ImageService;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.xml.namespace.QName;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/22.
 * 通过远程webservice调用api的情况
 */
@Component("cxfPostClient")
@Scope(value = "prototype")
public class CXFPostClient {
    @Value("${POSTSERVER.WS.URL}")
    private String wsurl;
    @Value("${POST.API.TYPE}")
    private String postType;

    @Value("${FTP.IP}")
    private String ftpIP;//图片服务器的ip
    @Value("${IMAGE_URL_PREFIX}")
    private String imageUrlPrefix;
    @Value("${IMG_MANAGER_WS_URL}")
    private  String imgManagerWsUrl;
    @Autowired
    private ImageService imageService;


    static Logger logger = Logger.getLogger(CXFPostClient.class);



    public String sendPostAction(List<BasicHeader> basicHeaders,String url,String xml){
        /**检查一下需要token的请求是否设置了token*/
        if (StringUtils.isBlank(xml)){
            logger.error("api请求的xml不能为空");
            return null;
        }
        if (MyStringUtil.containsSpecStr(xml, "<eBayAuthToken>")){
            String tok = MyStringUtil.getStringBetween2char(xml, "<eBayAuthToken>", "</eBayAuthToken>");
            if (StringUtils.isBlank(tok)||"null".equalsIgnoreCase(tok)){
                logger.error("api请求的token不能为空!"+xml);
                return null;
            }
        }


        /**如果设置成直接从本机请求*/
        if(StringUtils.isEmpty(postType) || "1".equalsIgnoreCase(postType)){
            PostApiByHttpClient byHttpClient=new PostApiByHttpClient();
            String r="";
            try {
                r=byHttpClient.postApi(basicHeaders,url,xml);
            } catch (Exception e) {
                logger.error("post出错！",e);
                return null;
            }
            return r;
        }
/**如果不是直接从本机请求，那么调用webservice*/
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(PostApiService.class);
        factory.setAddress(wsurl);
        PostApiService client= (PostApiService) factory.create();
        CXFUtils.configTimeout(client);

        CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
        cxfClientRequestVO.setHeaders(ApiHeader.header2Map(basicHeaders));
        cxfClientRequestVO.setXml(xml);
        cxfClientRequestVO.setUrl(url);

        String result =client.postApi(cxfClientRequestVO);
        if (StringUtils.isNotBlank(result)){
            try {
                result= URLDecoder.decode(result,"utf-8").replace("\u0007","");
                //result=StringEscapeUtils.unescapeHtml(result);
            } catch (Exception e) {
                logger.error("解码错误",e);
            }
        }
        return result;
    }

    /**获取帐号sku下的图片列表*/
    public List<String> findImagesBySku(String userid,String sku)throws Exception{
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if (userid==null){
            //userid=sessionVO.getLoginId();
            userid=imageService.getImageUserDir();
        }
        if (!userid.equalsIgnoreCase(((Long)sessionVO.getId()).toString())){
            logger.error("需要查看的账户和当前登录账户不匹配！");
            return null;
        }

        if (StringUtils.isBlank(userid)||StringUtils.isBlank(sku)){
            return null;
        }

        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        //Client client = factory.createClient("http://"+"img.tembin.com"+":8080/postApi/ws/postApiService?wsdl");
        Client client = factory.createClient(imgManagerWsUrl);
        Endpoint endpoint = client.getEndpoint();
        String operName="findImgList";

        QName qn= CXFUtils.filterQName(endpoint, operName);
        Object[] objs = client.invoke(qn, userid, sku);
        if (ObjectUtils.isLogicalNull(objs)||ObjectUtils.isLogicalNull(objs[0])){
            return null;
        }
        List<String> st= (List<String>) objs[0];
        return st;
    }
    /**获取帐号下的sku列表*/
    public List<String> findImagesDirByUser(String userid)throws Exception{
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if (userid==null){
            userid=imageService.getImageUserDir();
            //userid=sessionVO.getLoginId();
        }
        if (!userid.equalsIgnoreCase(((Long)sessionVO.getId()).toString())){
            logger.error("需要查看的账户和当前登录账户不匹配！");
            return null;
        }

        if (StringUtils.isBlank(userid)){
            return null;
        }

        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        //Client client = factory.createClient("http://"+"img.tembin.com"+":8080/postApi/ws/postApiService?wsdl");
        Client client = factory.createClient(imgManagerWsUrl);
        Endpoint endpoint = client.getEndpoint();
        String operName="findImgDirList";

        QName qn= CXFUtils.filterQName(endpoint, operName);
        Object[] objs = client.invoke(qn, userid);
        if (ObjectUtils.isLogicalNull(objs)||ObjectUtils.isLogicalNull(objs[0])){
            return null;
        }
        List<String> st= (List<String>) objs[0];
        return st;
    }

    /**删除图片*/
    public String delServerImg(String userid, String sku, List<String> ls)throws Exception{
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if (userid==null){
            userid=imageService.getImageUserDir();
            //userid=sessionVO.getLoginId();
        }
        if (!userid.equalsIgnoreCase(((Long)sessionVO.getId()).toString())){
            logger.error("需要查看的账户和当前登录账户不匹配！");
            return null;
        }

        if (StringUtils.isBlank(userid)||StringUtils.isBlank(sku)){
            return null;
        }
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        //Client client = factory.createClient("http://"+"img.tembin.com"+":8080/postApi/ws/postApiService?wsdl");
        Client client = factory.createClient(imgManagerWsUrl);
        Endpoint endpoint = client.getEndpoint();
        String operName="delFiles";
        QName qn= CXFUtils.filterQName(endpoint, operName);
        Object[] objs = client.invoke(qn, userid,sku,ls);
        if (ObjectUtils.isLogicalNull(objs)||ObjectUtils.isLogicalNull(objs[0])){
            return null;
        }
        return (String) objs[0];
    }





    public static void main(String[] args) throws Exception{
        /*JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        Client client = factory.createClient("http://localhost:8080/postApi/ws/postApiService?wsdl");
        Endpoint endpoint = client.getEndpoint();
        String operName="delFiles";

        QName qn= CXFUtils.filterQName(endpoint, operName);

        List<String> l=new ArrayList<>();
        l.add("2.txt");
        l.add("4.txt");
        Object[] objs = client.invoke(qn, "xxxxx","xx",l);
        List<String> st= (List<String>) objs[0];*/
        CXFClientRequestVO cxfClientRequestVO=new CXFClientRequestVO();
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(PostApiService.class);
        factory.setAddress("http://img.tembin.com:8080/postApi/ws/postApiService");
        PostApiService client= (PostApiService) factory.create();
        String result =client.postApi(cxfClientRequestVO);
        String x="";
    }
}
