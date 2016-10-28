package com.weixin.service.impl;

import com.weixin.service.MsgTextService;
import com.weixin.util.WeiXinStaticPairm;
import com.weixin.util.WeiXinStringUtil;
import com.weixin.util.XMLTemplate;
import com.weixin.vo.MsgVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.Date;

/**
 * Created by Administrator on 2015/5/22.
 * 微信文本消息处理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MsgTextServiceImpl implements MsgTextService{
    @Override
    /**新关注的用户处理，被动回复*/
    public String newUser(String reqBody){
        MsgVO msgVO=new MsgVO();
        msgVO.setMsgType(WeiXinStaticPairm.text);
        msgVO.setContent("谢谢关注TemBin!微信服务开发中，详情请访问主页http://tembin.com");
        msgVO.setToUserName(WeiXinStringUtil.getFromUserName(reqBody));
        msgVO.setFromUserName(WeiXinStringUtil.getCustomString(reqBody,"ToUserName"));
        return XMLTemplate.bdReturnMessage(msgVO);
    }

    @Override
    /**收到消息后被动回复*/
    public String replayMessage(String reqBody){
        MsgVO msgVO=new MsgVO();
        msgVO.setMsgType(WeiXinStaticPairm.text);
        msgVO.setContent("测试中！请访问 tembin.com !你发送的消息内容是："+WeiXinStringUtil.getCustomString(reqBody, "Content"));
        msgVO.setToUserName(WeiXinStringUtil.getFromUserName(reqBody));
        msgVO.setFromUserName(WeiXinStringUtil.getCustomString(reqBody,"ToUserName"));
        return XMLTemplate.bdReturnMessage(msgVO);
    }

}
