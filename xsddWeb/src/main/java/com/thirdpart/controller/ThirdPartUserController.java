package com.thirdpart.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.base.utils.exception.Asserts;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.utils.json.JsonUtils;
import com.common.base.web.BaseAction;
import com.thirdpart.service.ThirdPartUserService;
import com.thirdpart.vo.AccessKeyVO;
import com.thirdpart.vo.ThirdPartAccountVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/24.
 * 管理第三方用户
 */
@Controller
@RequestMapping("thirdpart")
public class ThirdPartUserController extends BaseAction {
    static Logger logger = Logger.getLogger(ThirdPartUserController.class);
    @Autowired
    private ThirdPartUserService thirdPartUserService;

    @RequestMapping("syncThirdPartAccount.do")
    @ResponseBody
    /**同步帐户*/
    public void syncThirdPartAccount(@RequestBody String requestBody,HttpServletResponse response){
        List<ThirdPartAccountVO> users = JSON.parseArray(requestBody, ThirdPartAccountVO.class);
        List<AccessKeyVO> usersMark=thirdPartUserService.sync3PartAccount(users);
        AjaxResponse.sendText(response, JsonUtils.toBrowserJson(usersMark, null));
    }


    @RequestMapping("generateAccessKey.do")
    @ResponseBody
    /**获取accesskey {"userMark":"caixu","thirdPartMark":"ggg"}*/
    public void generateAccessKey(@RequestBody String requestBody,HttpServletResponse response){
        Map<String,String> m = JSON.parseObject(requestBody, new TypeReference<Map<String, String>>() {});
        String userMark=m.get("userMark");
        String thirdPartMark=m.get("thirdPartMark");
        Asserts.assertTrue((StringUtils.isNotBlank(userMark)&&StringUtils.isNotBlank(thirdPartMark)),"empty");
        String k= thirdPartUserService.generateAccessKey(userMark, thirdPartMark);
        Map<String,String> map=new HashMap<>();
        map.put("accessKey",k);
        AjaxResponse.sendText(response, JsonUtils.toBrowserJson(map, null));
    }
    /**第三方用户登陆*/



}
