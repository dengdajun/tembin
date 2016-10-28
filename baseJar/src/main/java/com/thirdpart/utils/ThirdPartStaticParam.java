package com.thirdpart.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/29.
 * 一些第三方需要的静态参数
 */
public class ThirdPartStaticParam {
    /**允许第三方访问的url*/
    public static final List<String> ThirdPartUrl=new ArrayList<>();
    static {
        ThirdPartUrl.add("moduleManager.do");//模块管理
        ThirdPartUrl.add("information/itemInformationList.do");//商品列表
        ThirdPartUrl.add("information/pictureList.do");//图片管理
        ThirdPartUrl.add("keymove/userSelectSite.do");//一键搬家
        ThirdPartUrl.add("itemManager.do");//范本管理
        ThirdPartUrl.add("listingdataManager.do");//在线商品
        ThirdPartUrl.add("TemplateInitTableList.do");//刊登模板
        ThirdPartUrl.add("complement/complementManager.do");//自动补数
        ThirdPartUrl.add("sendMessage/sendMessageList.do");//消息模板
        ThirdPartUrl.add("userCases/userCasesList.do");//case管理
        ThirdPartUrl.add("assess/assessManager.do");//评价管理
        ThirdPartUrl.add("clientassess/clientAssessManager.do");//客户评价
        ThirdPartUrl.add("user/ebayAccountManager.do");//帐号绑定

    }
}
