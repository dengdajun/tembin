package com.base.utils.ftpabout.service.impl;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.ftpabout.service.FTPservice;
import com.base.utils.imageManage.service.ImageService;
import com.test.mapper2.TestFTPMapper;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/22.
 * ftp
 */
@Service("FTPservice")
@Transactional(value = "dataSourceFtp",rollbackFor = Exception.class)
public class FTPserviceImpl implements FTPservice {
    static Logger logger = Logger.getLogger(FTPserviceImpl.class);
    @Autowired
    private TestFTPMapper testFtpMapper;
    @Autowired
    private ImageService imageService;

    @Override
    /**修改密码*/
    public void changeFtpPassWord(Map map){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO != null, "session不能为空");
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(map.get("passw")), "密码不能为空！");
        String p=(String)map.get("passw");
        Asserts.assertTrue(p.length()>=6,"密码长度必须大于6位");
        map.put("userName", sessionVO.getLoginId());
        makeFtpDir();
        p=p.trim();
        map.put("passw",p);
        if (addFtpAccount(sessionVO.getLoginId(),p)){
            testFtpMapper.updateFtpPW(map);
        }
    }
    /**增加ftp帐号*/
public boolean addFtpAccount(String user,String passw){
    Map map=new HashMap();
    map.put("userName",user);
    map.put("passw",passw);
    List<Map> ac=testFtpMapper.queryFtpAccount(map);
    if (!ObjectUtils.isLogicalNull(ac)){
        return true;
    }
    testFtpMapper.addFtpAccount(map);
    return false;
}

    /**创建目录*/
    public void makeFtpDir() {
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO!=null,"session不能为空！");
        FTPClient ftpClient=null;
            try {
                ftpClient=getClient();
                if (ftpClient!=null) {
                    boolean skudir = ftpClient.changeWorkingDirectory(sessionVO.getLoginId());
                    if (!skudir) {
                        boolean cdSku2 = ftpClient.makeDirectory(sessionVO.getLoginId());
                        if (!cdSku2){
                            logger.error("创建ftp目录失败");
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("",e);
            } finally {
            if (ftpClient!=null){
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                }
            }
        }

    }

    /**返回一个ftpcleint*/
    public FTPClient getClient() throws Exception {
        FTPClient ftpClient = new FTPClient();
        Map<String, String> map=imageService.getFTPINfo();
        ftpClient.connect(map.get("ftpIP"), Integer.parseInt(map.get("ftpPort")));
        ftpClient.login(map.get("ftpUserName"), map.get("ftpPassword"));
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setUseEPSVwithIPv4(true);//java7

        int reply;
        reply=ftpClient.getReplyCode();
        if(!FTPReply.isPositiveCompletion(reply)){
            logger.error("ftp登陆失败"+reply);
            ftpClient.disconnect();
            return null;
        }

        return ftpClient;
    }

}
