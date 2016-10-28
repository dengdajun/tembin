package com.base.utils.common;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndian;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/16.
 */
public class MyFileUtils {
    /**获取文件或者目录列表,type为1是获取目录，为2是获取文件
     * Collection<File> c= FileUtils.listFiles(file, new String[]{"xml"}, false);更为简单的方法
     * */
    public static List<String> findFileOrDirList(String path, final String type) throws Exception{
        if (StringUtils.isBlank(path)){
            return null;
        }


        final List<String> f=new ArrayList<String>();

        Path startPath = Paths.get(path);
        Files.walkFileTree(startPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if ("1".equals(type)){
                    f.add(dir.toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if ("2".equals(type)){
                    f.add(file.getFileName().toString());
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file,IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
        if (f.size()>50){
            f.subList(0,49);
        }
        return f;
    }




    /**获取文件头信息开始==========================================================*/
    public static String getFileHeader(InputStream stream) throws Exception{

        /*POI的判断方式为
        long singer= LittleEndian.getLong(file.getBytes(), 0);
        String x= new String(HexDump.longToHex(singer));*/

        byte[] b= new byte[4];
        stream.read(b,0,b.length);
        return bytesToHexString(b);
    }
    /**
     * 将要读取文件头信息的文件的byte数组转换成string类型表示
     *
     * @param src
     *            要读取文件头信息的文件的byte数组
     * @return 文件头信息
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }
    /**获取文件头信息结束==========================================================*/

}
