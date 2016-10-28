package com.base.domains.DictQuery;

import java.util.List;

/**
 * Created by Administrator on 2015/4/7.
 * 查询重复运输方式时需要的条件
 */
public class DataDicShipingQueryParamVO {
    public Long siteID;
    public Long orgID;
    public List<String> shipingString;


    public Long getSiteID() {
        return siteID;
    }

    public void setSiteID(Long siteID) {
        this.siteID = siteID;
    }

    public Long getOrgID() {
        return orgID;
    }

    public void setOrgID(Long orgID) {
        this.orgID = orgID;
    }

    public List<String> getShipingString() {
        return shipingString;
    }

    public void setShipingString(List<String> shipingString) {
        this.shipingString = shipingString;
    }
}
