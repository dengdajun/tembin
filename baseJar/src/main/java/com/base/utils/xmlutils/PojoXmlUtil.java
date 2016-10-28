package com.base.utils.xmlutils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Created by Administrtor on 2014/7/16.
 */
public class PojoXmlUtil {
    /**
     * xml pojo对像转换成xml
     * @param cc
     * @param <T>
     * @return
     */
    public static<T> String pojoToXml(T... cc){
        StringBuffer sb=new StringBuffer();
        for (T c : cc) {
            XStream xs = new XStream();
            // XStream xstream=new XStream(new DomDriver()); //直接用jaxp dom来解释
           //  XStream xstream=new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
            xs.processAnnotations(c.getClass());
            sb.append(xs.toXML(c)).append("\n") ;
        }
        return sb.toString().trim();
    }

    /**xml转换成bean*/
    public static <T> T  toBean(String xmlStr,Class<T> cls){
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj=(T)xstream.fromXML(xmlStr);
        return obj;
    }
}
