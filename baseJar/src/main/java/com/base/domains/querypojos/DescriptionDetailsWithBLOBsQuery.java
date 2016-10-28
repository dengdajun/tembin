package com.base.domains.querypojos;

import com.base.database.trading.model. TradingDescriptionDetailsWithBLOBs;

/**
 * Created by cz on 2014/7/28.
 */
public class DescriptionDetailsWithBLOBsQuery extends  TradingDescriptionDetailsWithBLOBs{

    private Boolean isMainAcount;//当前账号是否是主账号

    private Long curAcountUserId;//当前账号userId;

    public Boolean getIsMainAcount() {
        return isMainAcount;
    }

    public void setIsMainAcount(Boolean isMainAcount) {
        this.isMainAcount = isMainAcount;
    }

    public Long getCurAcountUserId() {
        return curAcountUserId;
    }

    public void setCurAcountUserId(Long curAcountUserId) {
        this.curAcountUserId = curAcountUserId;
    }

}
