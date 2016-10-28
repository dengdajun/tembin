package com.base.utils.threadpool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/27.
 * ebay返回的错误代码
 */
public class EbayErrorCodeStatic {
    /**自动补数时，商品已下架*/
    public static final String list_item_end_code="291";
    /**帐户额度不够*/
    public static final String list_number_items_enough="21919188";
    /**商品描述信息中有特殊字符，不允许修改*/
    public static final String list_item_desc_code="240";
    /**纠纷IO异常错误码*/
    public static final String user_cases_io_code="1001";
    /**纠纷EBPIO异常错误码*/
    public static final String cases_ebp_io_code="1001";
    /**单个商品一天最多修改150次限额*/
    public static final String list_item_update_num_code="21919165";
    /**The requested data is currently not available due to an eBay system error. Please try again later*/
    public static final String currently_not_available="16100";
    /**Input transfer has been terminated because your request timed out*/
    public static final String request_timed_out="21359";
    /**Internal error to the application*/
    public static final String Internal_error="10007";
    /**The account for user ID specified in this request is suspended. Sorry, you can only request information for current users*/
    public static final String request_is_suspended841="841";

    /**警告：订单关联的商品已下架*/
    public static final String associated_Items_been_delete="21917182";



    /**===============================paypal错误嘛=================================================================*/
    //GetTransactionDetails没有权限访问交易信息
    public static final String PermissiondeniedErr="10007";
    //The transaction could not be loaded
    public static final String transactioncouldnotbeloaded="10004";


    /**一些只需要停顿较短时间的错误*/
    public static final Map<String,Integer> small_err=new HashMap<>();
    static {
        small_err.put(currently_not_available,2);
        small_err.put(request_timed_out,2);
        small_err.put(Internal_error,2);
        small_err.put(request_is_suspended841,600);

        //paypal
        small_err.put("paypal_"+PermissiondeniedErr,3600);
        small_err.put("paypal_"+transactioncouldnotbeloaded,2000);
    }


    /*ebayMessage 可控errorCode Map,21359超时*/
    public static final String[] messageArray={request_timed_out};
    /*order 可控errorCode Map*/
    public static final String[] orderArray={};
    /*订单item 可控errorCode Map*/
    public static final String[] orderItemArray={};
    /*订单amount 可控errorCode Map*/
    public static final String[] orderAcountArray={};
    /*订单外部交易 可控errorCode Map*/
    public static final String[] orderSellerTransactionArray={};
    /*纠纷目录 可控errorCode Map*/
    public static final String[] userCase={};
    /*EBP纠纷 可控errorCode Map*/
    public static final String[] EBPCase={};
    /*dispute纠纷 可控errorCode Map*/
    public static final String[] disputeCase={};


}
