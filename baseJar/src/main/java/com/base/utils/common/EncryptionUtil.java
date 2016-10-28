package com.base.utils.common;

import org.apache.commons.io.FileUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.io.*;

/**
 * 密码加密工具类
 * @author
 *
 */
public class EncryptionUtil {
    
    public static String pwdEncrypt(String password, String username) {
	ShaPasswordEncoder spe = new ShaPasswordEncoder(256);
	spe.setEncodeHashAsBase64(true);
	return spe.encodePassword(password, username);
    }

    /**md5编码用于编码图片地址*/
    public static String md5Encrypt(String url){
        Md5PasswordEncoder md5=new Md5PasswordEncoder();
        String m = md5.encodePassword(url,"");
        return m;
    }

    /**md5编码用于编码通用参数*/
    public static String md5Encrypt2(String p){
        Md5PasswordEncoder md5=new Md5PasswordEncoder();
        String m = md5.encodePassword(p,"tembin_");
        return m;
    }

    /**用于编码文件*/
    public static String md5ForFile(InputStream inputStream)throws Exception{
        /*ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > -1 ) {
            baos.write(buffer, 0, len);
        }
        baos.flush();*/
        byte[] bs= ConvertUtil.InputStreamTOByte(inputStream);
        return DigestUtils.md5DigestAsHex(bs);
    }
    
    public static void main(String[] args) throws Exception{
        System.out.println(pwdEncrypt("123456","zhaoxia_huang@hotmail.com"));
    }
}
