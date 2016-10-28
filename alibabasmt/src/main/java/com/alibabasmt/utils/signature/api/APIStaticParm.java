package com.alibabasmt.utils.signature.api;

/**
 * Created by Administrator on 2015/3/25.
 * 调用api用到的一些静态参数
 */
public class APIStaticParm {
    /**api的命名空间和版本号 数据格式*/
    public static final String apiNameSpace="param2/1/aliexpress.open/";
    /**system api的命名空间和版本号数据格式*/
    public static final String systemNameSpace="param2/1/system/";


    /**api的名称些=============================*/
    public static final String getChildrenPostCategoryById = apiNameSpace+"api.getChildrenPostCategoryById";//获取分类信息
    public static final String getAttributesResultByCateId = apiNameSpace+"api.getAttributesResultByCateId";//获取分类属性信息
    public static final String findProductInfoListQuery = apiNameSpace+"api.findProductInfoListQuery";//获取产品列表信息
    public static final String findAeProductById = apiNameSpace+"api.findAeProductById";//获取产品详细信息
    public static final String listFreightTemplate = apiNameSpace+"api.listFreightTemplate";//获取运输模板列表信息
    public static final String getFreightSettingByTemplateQuery = apiNameSpace+"api.getFreightSettingByTemplateQuery";//获取运输模板详细信息
    public static final String currentTime = systemNameSpace+"currentTime";//获取系统时间
    public static final String queryAeAnouncement=apiNameSpace+"api.queryAeAnouncement";//查询平台公告
    public static final String findOrderListSimpleQuery=apiNameSpace+"api.findOrderListSimpleQuery"; //订单列表简化查询
    public static final String findOrderById=apiNameSpace+"api.findOrderById"; //根据orderId查订单详情
    public static final String sellerShipment=apiNameSpace+"api.sellerShipment"; //根据orderId查订单详情
    public static final String addOrderMessage=apiNameSpace+"api.addOrderMessage";//订单发送留言
    public static final String addMessage=apiNameSpace+"api.addMessage";//发送站内信
    public static final String queryMessageList=apiNameSpace+"api.queryMessageList";//查询站内信

}
