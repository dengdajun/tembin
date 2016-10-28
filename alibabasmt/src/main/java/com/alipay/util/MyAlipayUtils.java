package com.alipay.util;

import com.alipay.config.AlipayConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/5/28.
 */
public class MyAlipayUtils {
    /**组装订单参数map*/
    public static Map<String,String> returnParmMap(){
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "create_direct_pay_by_user");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("seller_email", AlipayConfig.seller_email);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);

        return sParaTemp;
    }
}
