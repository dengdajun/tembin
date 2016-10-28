package com.base.utils.common;

import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.weixin.util.WeiXinStringUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrtor on 2014/8/26.
 */
public class MyStringUtil {
    static Logger logger = Logger.getLogger(MyStringUtil.class);


    /**判断一些特殊的空字符串*/
    public static boolean specEmptyString(String str){
        if (StringUtils.equalsIgnoreCase("null",str)||
                StringUtils.equalsIgnoreCase("undefined",str)){
            return true;
        }
        return false;
    }

    /**根据日期时间生成一个文件名*/
    public static String generateRandomFilename(){
        String fourRandom = "";
        //产生4位的随机数(不足4位前加零)
        int   randomNum =   (int)(Math.random()*10000);
        fourRandom = randomNum +"";
        fourRandom=StringUtils.leftPad(fourRandom,4,"0");
        StringBuilder sb = new StringBuilder("");
        sb.append(DateUtils.getFullYear(new Date()))
                .append(twoNumbers(DateUtils.getMonth(new Date())))
                .append(twoNumbers(DateUtils.getDayOfMonth(new Date())))
                .append(twoNumbers(DateUtils.getHour(new Date())))
                .append(twoNumbers(DateUtils.getMinute(new Date())))
                .append(twoNumbers(DateUtils.getSecond(new Date())))
                .append(fourRandom);
        return sb.toString();
    }
    private static String twoNumbers(int number){
        String _number = number + "";
        if(_number.length() < 2){
            _number = "0" + _number;
        }
        return _number;
    }


    /**提取后缀*/
    public static String getExtension(String filename, String defExt) {
        if ((filename != null) && (filename.length() > 0)) {
            int i = filename.lastIndexOf('.');

            if ((i >-1) && (i < (filename.length() - 1))) {
                return filename.substring(i );
            }
        }
        return defExt.toLowerCase();
    }
    /**提取文件名，不包括后缀*/
    public static String getFimeNoStuff(String fileName){
        String stuff=getExtension(fileName, "");
        return fileName.replace(stuff,"").toLowerCase();
    }

    /**产生指定规则的随机数包含字母和数字****************************************************
     * //产生5位长度的随机字符串，中文环境下是乱码
     RandomStringUtils.random(5);
     //使用指定的字符生成5位长度的随机字符串
     RandomStringUtils.random(5, new char[]{'a','b','c','d','e','f', '1', '2', '3'});
     //生成指定长度的字母和数字的随机组合字符串
     RandomStringUtils.randomAlphanumeric(5);
     //生成随机数字字符串
     RandomStringUtils.randomNumeric(5);
     //生成随机[a-z]字符串，包含大小写
     RandomStringUtils.randomAlphabetic(5);
     //生成从ASCII 32到126组成的随机字符串
     RandomStringUtils.randomAscii(4)
     * */
    public static String getRandomStringAndNum(int n){
        String b= RandomStringUtils.randomAlphanumeric(n);
        return b;
    }


    /**提取指定符号之间的内容(只取第一个)*/
    public static String getStringBetween2char(String sourceStr,String start,String end){
        if(StringUtils.isEmpty(sourceStr)){return null;}
        String rex="\\"+start+"(.*?)\\"+end;
        Pattern p = Pattern.compile(rex);
        Matcher m = p.matcher(sourceStr);
        if (m.find()){
            return m.group(1);
        }
        return null;
        /*while(m.find()){
            System.out.println(m.group(1));
        }*/

    }
    /**功能同上*/
    public static String subStringBetween(String s,String o,String c){
        String ss= StringUtils.substringBetween(s, o, c);
        return ss;
    }
    public static List<String> subStringsBetween(String s,String o,String c){
        String[] ss= StringUtils.substringsBetween(s, o, c);
        if (ss!=null){
            return Arrays.asList(ss);
        }else {
            return null;
        }

    }



    /**判断字符串中是否包含指定字符*/
    public static boolean containsSpecStr(String str,String conrain){
        return StringUtils.containsIgnoreCase(str, conrain);
    }

    /**
     * 替换模板变量
     *
     * @param template
     * @param data
     * @return
     */
    public static String replaceArgs(String template, Map<String, String> data){
        // sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
        StringBuffer sb = new StringBuffer();
        try{
            Pattern pattern = Pattern.compile("\\{(.+?)\\}");
            Matcher matcher = pattern.matcher(template);
            while(matcher.find()){
                String name = matcher.group(1);// 键名
                String value = (String)data.get(name);// 键值
                if(value == null){
                    continue;
                   // value = "";
                }else{
                    value = value.replaceAll("\\{", "\\\\\\{").replaceAll("\\$", "\\\\\\$");
                    //value = value.replaceAll("\\$", "\\\\\\$");//这个是特殊字符
                }
                matcher.appendReplacement(sb, value);
            }
            // 最后还得要把尾串接到已替换的内容后面去，”
            matcher.appendTail(sb);
        }catch(Exception e){
           logger.error("在替换字符串的时候报错",e);
        }
        return sb.toString() ;   //加一个空行（结束行）
    }

/**组装jqueryupload组件的数据*/
public static String makeJqueryUpLoadData(MultipartFile multipartFile,String pname,String url,String delUrl){

    String ext=MyStringUtil.getExtension(url, null);
    String name=MyStringUtil.getFimeNoStuff(url);
    String fn=name+ext;

    String viewUrl=url.replace(fn,name+"_small"+ext);

    StringBuffer sb=new StringBuffer();
    sb.append("{\"url\":").append("\""+url+"\",");

    sb.append("\"thumbnailUrl\":").append("\""+viewUrl+"\",");
    sb.append("\"name\":").append("\""+pname+"\",");
    sb.append("\"type\":").append("\""+multipartFile.getContentType()+"\",");
    sb.append("\"size\":").append(multipartFile.getSize()+",");
    sb.append("\"deleteUrl\":").append("\"" +delUrl + "\",");
    sb.append("\"deleteType\":").append("\"DELETE\"}");
    return sb.toString();
}

    /**不区分大小写替换第一个出现的字符串*/
    public static String replaceFirstIgnoreCase(String str,String str1,String str2){
        if (StringUtils.isBlank(str)){return str;}
        return str.replaceFirst("(?i)"+str1+"",str2);
    }
    /**不区分大小写替换所有出现的字符串*/
    public static String replaceAllIgnoreCase(String str,String str1,String str2){
        if (StringUtils.isBlank(str)){return str;}
        return str.replaceAll("(?i)" + str1 + "", str2);
    }

    public static void main(String[] args) {
        String s="Select selEct select a.*,jhj from from asfdsaf";
        s=replaceAllIgnoreCase(s,"select","dd");
        System.out.println(StringUtils.substringBetween("Select a.*,jhj from from asfdsaf","select","from"));
    }
}
