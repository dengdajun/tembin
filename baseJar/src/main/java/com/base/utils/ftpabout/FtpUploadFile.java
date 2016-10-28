package com.base.utils.ftpabout;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.ConvertUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.imageManage.ImageUtil;
import com.base.utils.imageManage.service.ImageService;
import com.base.utils.imageManage.service.impl.ImageServiceImpl;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/13.
 */
public class FtpUploadFile {
    static Logger logger = Logger.getLogger(FtpUploadFile.class);
    static ThreadLocal<FTPClient> ftc=new ThreadLocal();


    public static FTPClient  getC() throws Exception{
        ImageService imageService1= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
        Map<String,String> map=imageService1.getFTPINfo();
        FTPClient ftpClient =new FTPClient();
        ftpClient.connect(map.get("ftpIP"),Integer.parseInt(map.get("ftpPort")));
        ftpClient.login(map.get("ftpUserName"), map.get("ftpPassword"));
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setConnectTimeout(20000);
        ftpClient.setDefaultTimeout(20000);
        ftpClient.setDataTimeout(20000);
        ftpClient.setUseEPSVwithIPv4( true );//java7在windows上的一个bug
        return ftpClient;
    }

    /**ftp上传文件*/
    public static String ftpUploadFile(InputStream inputStream ,String skuName,String stuff) throws Exception {
        String fileName="";
        FTPClient ftpClient = ftc.get();
        try {
            if (ftpClient==null||!ftpClient.isConnected()){
                ftc.remove();
                ftpClient=getC();
                ftc.set(ftpClient);
            }

            logger.error("ftpClient"+ftpClient.getStatus());
            int reply;//检查登陆结果
            reply=ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)){
                logger.error("ftp登陆失败!登陆结果是"+reply);
                ftpClient.disconnect();
                return null;
            }
            if(skuName.indexOf("/")>-1){
                skuName=skuName.toLowerCase();
                skuName.replace("?set_id=880000500f","").replace("?set_id=880000500F", "");
                stuff=stuff.toLowerCase();
                stuff.replace("?set_id=880000500f","").replace("?set_id=880000500F","");
                if(stuff.indexOf("?")>-1){
                    String r=stuff.substring(stuff.lastIndexOf("?"), stuff.length());
                    stuff=stuff.replace(r,"");
                }

                String[] dnames=skuName.split("/");
                boolean skudir = ftpClient.changeWorkingDirectory(dnames[0]);
                if("null".equalsIgnoreCase(skuName)){
                    return null;
                }
                if(!skudir){
                    boolean cdSku = ftpClient.makeDirectory(dnames[0]);
                    if(cdSku){
                        ftpClient.changeWorkingDirectory(dnames[0]);
                        boolean cdSku2 = ftpClient.makeDirectory(dnames[1]);
                        if(cdSku2){
                            ftpClient.changeWorkingDirectory(dnames[1]);
                        }else {
                            logger.error("在ftp上创建sku目录失败1"+":"+dnames[1]);
                            closeFtp(ftpClient);
                            return null;
                        }

                    }else {
                        logger.error("在ftp上创建sku目录失败2"+":"+dnames[0]);
                        closeFtp(ftpClient);
                        return null;
                    }

                }else {
                    boolean cdSku2 = ftpClient.changeWorkingDirectory(dnames[1]);
                    if(!cdSku2){
                        boolean cdSku3 = ftpClient.makeDirectory(dnames[1]);
                        if(!cdSku3){
                            logger.error("在ftp上创建sku目录失败3"+":"+skuName);
                            closeFtp(ftpClient);
                            return null;
                        }
                    }
                    ftpClient.changeWorkingDirectory(dnames[1]);
                }
            }else {
                boolean skudir = ftpClient.changeWorkingDirectory(skuName);
                if(!skudir){
                    boolean cdSku = ftpClient.makeDirectory(skuName);
                    if(!cdSku){
                        logger.error("在ftp上创建sku目录失败4"+":"+skuName);
                        closeFtp(ftpClient);
                        return null;
                    }
                }
                ftpClient.changeWorkingDirectory(skuName);
            }
            // String[] x= ftpClient.listNames();
            // String fileName = new String(file.getName().getBytes("utf-8"),"iso-8859-1");
            fileName= MyStringUtil.generateRandomFilename()+stuff;
            //inputStream.mark(1);
            byte[] bint=ConvertUtil.InputStreamTOByte(inputStream);
            if (bint==null||bint.length==0){
                throw new Exception("没有图片数据流");
            }
            InputStream smin= ConvertUtil.byteTOInputStream(bint) ;
            InputStream smin1=ConvertUtil.byteTOInputStream(bint) ;
            //smin.reset();
            //smin1.reset();
            boolean  result = ftpClient.storeFile(fileName, smin);
            boolean  result1;
            try {
                InputStream xxx=ImageUtil.zoomPic(smin1, 80, 80);
                result1 = ftpClient.storeFile(MyStringUtil.getFimeNoStuff(fileName)+"_small"+MyStringUtil.getExtension(fileName, ""),
                        xxx);
            } catch (Exception e) {
                result1=false;
                logger.error("ftp上传小图失败!直接上传成大图!"+fileName,e);
            }
            if (result) {
                logger.info("ftp上传成功!");
            }
            if (!result1){
                ftpClient.storeFile(MyStringUtil.getFimeNoStuff(fileName)+"_small"+MyStringUtil.getExtension(fileName,""),
                        ConvertUtil.byteTOInputStream(bint));
            }
        } catch (Exception e) {
            logger.error("ftp出错"+e.getMessage(),e);
            throw new Exception(e.getMessage(),e);
            //return null;

        } finally {
            /*try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }*/
        }

        return fileName;
    }

    public static void closeFtp(FTPClient ftpClient){
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }


    /**通过url的方式上传图片*/
    public static String uploadpicByurl(String url){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ImageServiceImpl.usFtpServerIP, 21);
            ftpClient.login(ImageServiceImpl.usFtpServerUser, ImageServiceImpl.usFtpServerPassword);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setUseEPSVwithIPv4(true);//java7在windows上的一个bug

            int reply;//检查登陆结果
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.error("ftp登陆失败!登陆结果是" + reply);
                ftpClient.disconnect();
                return "loginerr";
            }
            ftpClient.changeWorkingDirectory("/var/www/html/");// 转移到FTP服务器目录
            String n= StringUtils.substringAfterLast(url, "/");
            String[] s= ftpClient.listNames(n);
            if (!ObjectUtils.isLogicalNull(s)){
                return "ok";
            }

            URL url1 = new URL(url);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = null;
            inStream = conn.getInputStream();

            boolean b = ftpClient.storeFile(n,inStream);
            return b?"ok":"err";
        } catch (Exception e){
            logger.error(e);
            return "err";
        }finally {
            closeFtp(ftpClient);
        }

    }


    public static void main(String[] args) {
        uploadpicByurl("/tuyu.txt");
    }

}
