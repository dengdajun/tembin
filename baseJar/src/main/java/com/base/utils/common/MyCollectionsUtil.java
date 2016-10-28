package com.base.utils.common;




import com.base.database.userinfo.model.UsercontrollerUser;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/11.
 */
public class MyCollectionsUtil {

    /**list去重,对象*/
    public static<T> List<T> listUnique(List<T> list,String s) throws Exception{
        if (ObjectUtils.isLogicalNull(list)||ObjectUtils.isLogicalNull(s)){
            throw new Exception("不能为空！无法过滤！");
        }

        List<T> l=new ArrayList<>();

        List<String> ltem=new ArrayList<>();
        for (T t:list){
            String te=BeanUtils.getSimpleProperty(t,s);
            if (ObjectUtils.isLogicalNull(l)){
                l.add(t);
                ltem.add(te);
                continue;
            }

            if (ltem.contains(te)){
                continue;
            }else {
                l.add(t);
                ltem.add(te);
                continue;
            }

        }


        /*Map<String,String> map=new HashMap<>();

        for (T t:list){
            map.clear();
            for (String s:args){
                map.put(s, BeanUtils.getSimpleProperty(t,s));
            }

            if (ObjectUtils.isLogicalNull(l)){
                l.add(t);
                continue;
            }

            for (T t1 : l){
                int i=0;
                int argsNum=args.length;

                for (Map.Entry<String,String> entry : map.entrySet()){
                    String key=entry.getKey();
                    String value = entry.getValue();

                    String v1 = BeanUtils.getSimpleProperty(t1, key);
                    if (StringUtils.isBlank(value) && StringUtils.isBlank(v1)){
                        i++;
                        continue;
                    }
                    if (StringUtils.isNotBlank(value)&&value.equalsIgnoreCase(v1)){
                        i++;
                        continue;
                    }
                }
                if (i==argsNum){
                    continue;
                }
                else {
                    l.add(t);
                    break;
                }
            }

        }*/

return l;

    }

    /**list去重，利用Set*/
    public static<T> List<T> listUnique(List<T> list){
        if (list==null){return list;}
        Set<T> set=new LinkedHashSet<T>();
        for (T t:list){
            set.add(t);
        }
        return new ArrayList<T>(set);
    }

    /**多个集合取交集*/
    public static <T> List<T> listRetain(List<T>[] list){
        int i=0;
        for (;;){
            if (i>list.length-2){break;}
            list[0].retainAll(list[i+1]);
            i++;
            if (i>50){break;}
        }
        List<T> ttt=list[0];
        return ttt;
    }

    /**两个个集合取差集*/
    public static <T> List<T> listRetain(List<T> listA,List<T> listB){
        listA.removeAll(listB);
        return listA;
    }

    /**
     *
      * @param list  集合
     * @param pageSize 每个子列表条数
     * @return
     */
    public static  <T>  List<List<T>> splitList(List<T> list, int pageSize) {
        int total = list.size();
        //总页数
        int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<List<T>> result=new ArrayList<>();
        for(int i = 0; i < pageCount; i++) {
            int start = i * pageSize;
            //最后一条可能超出总数
            int end = start + pageSize > total ? total : start + pageSize;
            List<T> subList = list.subList(start, end);
            List<T> x=new ArrayList<>();
            x.addAll(subList);
            result.add(x);
        }
        return result;
    }

}
