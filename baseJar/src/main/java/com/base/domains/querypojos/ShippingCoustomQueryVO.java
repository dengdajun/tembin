package com.base.domains.querypojos;

/**
 * Created by Administrator on 2015/4/7.
 * 判断运输方式是否重复时，查询运输方式所用的VO
 */
public class ShippingCoustomQueryVO {
    public Long id;
    public Long shippingService; //与数据字典相关联的id
    public Long parentID;//主记录id
    public Long cuser;//创建记录人
    public String shipingName;//运输方式的名称
    public String site;//站点id
    public Long orgID;//公司id
    public String value;//数据字典的值

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShippingService() {
        return shippingService;
    }

    public void setShippingService(Long shippingService) {
        this.shippingService = shippingService;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
    }

    public Long getCuser() {
        return cuser;
    }

    public void setCuser(Long cuser) {
        this.cuser = cuser;
    }

    public String getShipingName() {
        return shipingName;
    }

    public void setShipingName(String shipingName) {
        this.shipingName = shipingName;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Long getOrgID() {
        return orgID;
    }

    public void setOrgID(Long orgID) {
        this.orgID = orgID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
