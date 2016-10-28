package com.thirdpart.utils;

import com.base.domains.PermissionVO;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.thirdpart.vo.ThirdPartAccountVO;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Created by Administrator on 2015/4/27.
 * 一些第三方处理会用到的工具
 */
public class ThirtPartUtil {
    /**分离出主帐号*/
    public static ThirdPartAccountVO findMainAccount(List<ThirdPartAccountVO> thirdPartAccountVOList){
        if (thirdPartAccountVOList==null){return null;}
        for (ThirdPartAccountVO vo:thirdPartAccountVOList){
            if (StringUtils.isBlank(vo.getParentID())){
                return vo;
            }
        }
        return null;
    }
    /**将时间拆分成年月日时的map*/
    public static Map<String,String> formatDate2YMDH(Date date){
        if (date==null){date=new Date();}
        Integer y= DateUtils.getFullYear(date);
        Integer m=DateUtils.getMonth(date);
        Integer d=DateUtils.getDayOfMonth(date);
        Integer H=DateUtils.getHour(date);
        Integer Mi=DateUtils.getMinute(date);
        Map map=new HashMap();
        map.put("year",y.toString());
        map.put("month",m.toString());
        map.put("day",d.toString());
        map.put("hour",H.toString());
        map.put("minute",Mi.toString());
        return map;
    }

    /**过滤出不允许第三方访问的地址*/
  public static List<String> denyThirdPart(){
      UserInfoServiceMapper userInfoServiceMapper= ApplicationContextUtil.getBean(UserInfoServiceMapper.class);
      Map map=new HashMap<>();
      map.put("trueall", "all");
      List<PermissionVO> pvs = userInfoServiceMapper.queryAllPermission(map);

      List<String> ulist=new ArrayList<>();
      Iterator<PermissionVO> it= pvs.iterator();
      while (it.hasNext()){
          PermissionVO pv=it.next();
          if (!ThirdPartStaticParam.ThirdPartUrl.contains(pv.getPermissionURL())){
              //it.remove();
              ulist.add(pv.getPermissionURL());
          }
      }
     return ulist;
  }

    public static void main(String[] args) {
        Map<String,String> m=formatDate2YMDH(new Date());
        String dt=m.get("year")+m.get("month")+m.get("day")+m.get("hour");
        System.out.println(dt);
    }
}
