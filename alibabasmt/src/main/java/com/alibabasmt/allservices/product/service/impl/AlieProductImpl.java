package com.alibabasmt.allservices.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibabasmt.allservices.product.service.IAlieProduct;
import com.alibabasmt.allservices.productlisting.service.ISmtProductListing;
import com.alibabasmt.database.customsmt.mapper.SmtProductQueryMapper;
import com.alibabasmt.database.smtproduct.mapper.SmtAeopSkuPropertyMapper;
import com.alibabasmt.database.smtproduct.mapper.SmtAeopaeProductPropertyMapper;
import com.alibabasmt.database.smtproduct.mapper.SmtAeopaeProductSkuMapper;
import com.alibabasmt.database.smtproduct.mapper.SmtProductMapper;
import com.alibabasmt.database.smtproduct.model.*;
import com.alibabasmt.domains.querypojos.smtproduct.SmtProductQuery;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.mybatis.page.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/19.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AlieProductImpl implements IAlieProduct {
    static Logger logger = Logger.getLogger(AlieProductImpl.class);
    @Autowired
    private SmtProductMapper smtProductMapper;
    @Autowired
    private SmtAeopaeProductPropertyMapper smtAeopaeProductPropertyMapper;
    @Autowired
    private SmtAeopaeProductSkuMapper smtAeopaeProductSkuMapper;
    @Autowired
    private SmtAeopSkuPropertyMapper smtAeopSkuPropertyMapper;
    @Autowired
    private ISmtProductListing iSmtProductListing;
    @Autowired
    private SmtProductQueryMapper smtProductQueryMapper;
    /**
     * 查询分类，从第一层查起
     * @param cateid
     * @return
     */
    @Override
    public List<Map> queryCategoryByIdList(String cateid,long smtAccountId){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.getChildrenPostCategoryById);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        m.put("cateId",cateid);
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        List<Map> lim = (List<Map>)jsons.get("aeopPostCategoryList");
        return lim;
    }

    /**
     * 查询产品的分类属性
     * @param cateid
     * @return
     */
    @Override
    public List<Map> queryCategoryAttrByIdList(String cateid,long smtAccountId){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.getAttributesResultByCateId);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        m.put("cateId",cateid);
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        List<Map> lim = (List<Map>)jsons.get("attributes");
        return lim;
    }

    @Override
    public Map queryByProductId(long productId, long smtAccountId){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.findAeProductById);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        m.put("productId",productId+"");
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        return jsons;
    }

    @Override
    public void saveSynchronizeProduct(long productId, long smtAccountId,SmtProductListing smtProductListing) throws Exception {
        Map map = this.queryByProductId(productId,smtAccountId);
        if(map!=null&&"true".equals(map.get("success")+"")){
            SmtProduct sp = this.contToSmtProduct(map,smtAccountId);
            if(sp!=null){
                this.saveSmtProduct(sp);
                if(sp.getId()!=null){//处理多属性，分类属性的数据
                    List<Map> listpro = (List<Map>) map.get("aeopAeProductPropertys");//分类属性
                    if(listpro!=null&&listpro.size()>0){
                        SmtAeopaeProductPropertyExample sappe = new SmtAeopaeProductPropertyExample();
                        sappe.createCriteria().andParentIdEqualTo(sp.getId());
                        List<SmtAeopaeProductProperty> lisapp = this.smtAeopaeProductPropertyMapper.selectByExample(sappe);
                        if(lisapp!=null&&lisapp.size()>0){
                            this.smtAeopaeProductPropertyMapper.deleteByExample(sappe);
                        }
                        for(Map pro:listpro){
                            SmtAeopaeProductProperty sapp = new SmtAeopaeProductProperty();
                            if(pro.get("attrNameId")!=null){
                                sapp.setAttrnameid(Long.parseLong(pro.get("attrNameId")+""));
                            }
                            if(pro.get("attrName")!=null){
                                sapp.setAttrname(pro.get("attrName") + "");
                            }
                            if(pro.get("attrValueId")!=null){
                                sapp.setAttrvalueid(Long.parseLong(pro.get("attrValueId") + ""));
                            }
                            if(pro.get("attrValue")!=null){
                                sapp.setAttrvalue(pro.get("attrValue") + "");
                            }
                            sapp.setParentId(sp.getId());
                            sapp.setCreateUser(sp.getCreateUser());
                            sapp.setCreateTime(new Date());
                            sapp.setUpdateTime(new Date());
                            this.smtAeopaeProductPropertyMapper.insertSelective(sapp);
                        }
                    }
                    List<Map> listsku = (List<Map>) map.get("aeopAeProductSKUs");
                    if(listsku!=null&&listsku.size()>0){
                        SmtAeopaeProductSkuExample sapse = new SmtAeopaeProductSkuExample();
                        sapse.createCriteria().andParentIdEqualTo(sp.getId());
                        List<SmtAeopaeProductSku> lisaps = this.smtAeopaeProductSkuMapper.selectByExample(sapse);
                        if(lisaps!=null&&lisaps.size()>0){
                            for(SmtAeopaeProductSku saps:lisaps){
                                SmtAeopSkuPropertyExample sspe = new SmtAeopSkuPropertyExample();
                                sspe.createCriteria().andParentIdEqualTo(saps.getId());
                                List<SmtAeopSkuProperty> lisasp = this.smtAeopSkuPropertyMapper.selectByExample(sspe);
                                if(lisasp!=null&&lisasp.size()>0){
                                    this.smtAeopSkuPropertyMapper.deleteByExample(sspe);
                                }
                            }
                            this.smtAeopaeProductSkuMapper.deleteByExample(sapse);
                        }
                        for(Map skum : listsku){
                            SmtAeopaeProductSku saps = new SmtAeopaeProductSku();
                            saps.setParentId(sp.getId());
                            saps.setCreateTime(new Date());
                            saps.setCreateUser(sp.getCreateUser());
                            saps.setUpdateTime(new Date());
                            if(skum.get("skuPrice")!=null){
                                saps.setSkuprice(Double.parseDouble(skum.get("skuPrice") + ""));
                            }
                            if(skum.get("ipmSkuStock")!=null){
                                saps.setIpmskustock(Long.parseLong(skum.get("ipmSkuStock") + ""));
                            }
                            if(skum.get("skuCode")!=null){
                                saps.setSkucode(skum.get("skuCode") + "");
                            }
                            if(skum.get("skuStock")!=null){
                                saps.setSkustock("true".equals(skum.get("skuStock") + "") ? "1" : "0");
                            }
                            this.smtAeopaeProductSkuMapper.insertSelective(saps);
                            List<Map> listm = (List<Map>) skum.get("aeopSKUProperty");
                            if(listm!=null&&listm.size()>0){
                                for(Map m:listm){
                                    SmtAeopSkuProperty sas = new SmtAeopSkuProperty();
                                    sas.setParentId(saps.getId());
                                    sas.setCreateUser(sp.getId());
                                    sas.setCreateTime(new Date());
                                    sas.setUpdateTime(new Date());
                                    if(m.get("skuPropertyId")!=null){
                                        sas.setSkupropertyid(Long.parseLong(m.get("skuPropertyId") + ""));
                                    }
                                    if(m.get("propertyValueId")!=null){
                                        sas.setPropertyvalueid(Long.parseLong(m.get("propertyValueId") + ""));
                                    }
                                    if(skum.get("propertyValueDefinitionName")!=null){
                                        sas.setPropertyvaluedefinitionname(m.get("propertyValueDefinitionName") + "");
                                    }
                                    if(skum.get("skuImage")!=null){
                                        sas.setSkuimage(m.get("skuImage") + "");
                                    }
                                    this.smtAeopSkuPropertyMapper.insertSelective(sas);
                                }
                            }
                        }
                    }
                }else{
                    logger.error("主表smt_product数据保存失败！");
                    throw new Exception("主表smt_product数据保存失败");
                }
                smtProductListing.setTaskFlag("1");
                this.iSmtProductListing.saveSmtProductListing(smtProductListing);
            }
        }else{
            logger.error("保存在线产品详细信息出错！");
            throw new Exception("保存在线产品详细信息出错！");
        }
    }

    public SmtProduct contToSmtProduct(Map map,long smtAccountId){
        String productId = "";
        SmtProduct sp = null;
        if(map.get("productId")!=null){
            productId = map.get("productId") + "";
        }
        if(productId==null||"".equals(productId)){
            return null;
        }
        SmtProductExample spe = new SmtProductExample();
        spe.createCriteria().andProductidEqualTo(Long.parseLong(productId));
        List<SmtProduct> lisp = this.smtProductMapper.selectByExampleWithBLOBs(spe);
        if(lisp!=null&&lisp.size()>0){
            sp = lisp.get(0);
        }else{
            sp = new SmtProduct();
            sp.setCreateTime(new Date());
        }
        if(sp==null){
            return null;
        }
        sp.setUpdateTime(new Date());
        sp.setCreateUser(smtAccountId);
        if(map.get("productId")!=null){
            sp.setProductid(Long.parseLong(productId));
        }
        if(map.get("detail")!=null){
            sp.setAlieDetail(map.get("detail")+"");
        }
        if(map.get("summary")!=null){
            sp.setSummary(map.get("summary") + "");
        }
        if(map.get("lotNum")!=null){
            sp.setLotnum(Integer.parseInt(map.get("lotNum") + ""));
        }
        if(map.get("bulkOrder")!=null){
            sp.setBulkorder(Integer.parseInt(map.get("bulkOrder") + ""));
        }
        if(map.get("packageType")!=null){
            sp.setPackagetype("true".equals(map.get("packageType") + "") ? "1" : "0");
        }
        if(map.get("freightTemplateId")!=null){
            sp.setFreighttemplateid(Long.parseLong(map.get("freightTemplateId") + ""));
        }
        if(map.get("subject")!=null){
            sp.setSubject(map.get("subject") + "");
        }
        if(map.get("productUnit")!=null){
            sp.setProductunit(Long.parseLong(map.get("productUnit") + ""));
        }
        if(map.get("wsOfflineDate")!=null){
            sp.setWsofflinedate(map.get("wsOfflineDate") + "");
        }
        if(map.get("packageLength")!=null){
            sp.setPackagelength(Integer.parseInt(map.get("packageLength") + ""));
        }
        if(map.get("wsDisplay")!=null){
            sp.setWsdisplay(map.get("wsDisplay") + "");
        }
        if(map.get("isImageDynamic")!=null){
            sp.setIsimagedynamic("true".equals(map.get("isImageDynamic") + "") ? "1" : "0");
        }
        if(map.get("packageHeight")!=null){
            sp.setPackageheight(Integer.parseInt(map.get("packageHeight") + ""));
        }
        if(map.get("packageWidth")!=null){
            sp.setPackagewidth(Integer.parseInt(map.get("packageWidth") + ""));
        }
        if(map.get("isPackSell")!=null){
            sp.setIspacksell("true".equals(map.get("isPackSell") + "") ? "1" : "0");
        }
        if(map.get("isImageWatermark")!=null){
            sp.setIsimagewatermark("true".equals(map.get("isImageWatermark") + "") ? "1" : "0");
        }
        if(map.get("ownerMemberSeq")!=null){
            sp.setOwnermemberseq(Long.parseLong(map.get("ownerMemberSeq") + ""));
        }
        if(map.get("categoryId")!=null){
            sp.setCategoryid(Long.parseLong(map.get("categoryId") + ""));
        }
        if(map.get("keyword")!=null){
            sp.setKeyword(map.get("keyword") + "");
        }
        if(map.get("imageURLs")!=null){
            sp.setImageurls(map.get("imageURLs") + "");
        }
        if(map.get("productStatusType")!=null){
            sp.setProductstatustype(map.get("productStatusType") + "");
        }
        if(map.get("ownerMemberId")!=null){
            sp.setOwnermemberid(map.get("ownerMemberId") + "");
        }
        if(map.get("grossWeight")!=null){
            sp.setGrossweight(Double.parseDouble(map.get("grossWeight") + ""));
        }
        if(map.get("deliveryTime")!=null){
            sp.setDeliverytime(Integer.parseInt(map.get("deliveryTime")+""));
        }
        if(map.get("wsValidNum")!=null){
            sp.setWsvalidnum(Integer.parseInt(map.get("wsValidNum") + ""));
        }
        if(map.get("bulkDiscount")!=null){
            sp.setBulkdiscount(Integer.parseInt(map.get("bulkDiscount") + ""));
        }
        if(map.get("promiseTemplateId")!=null){
            sp.setPromisetemplateid(Long.parseLong(map.get("promiseTemplateId") + ""));
        }
        if(map.get("productPrice")!=null){
            sp.setProductprice(Double.parseDouble(map.get("productPrice") + ""));
        }
        return sp;
    }

    @Override
    public void saveSmtProduct(SmtProduct smtProduct){
        if(smtProduct.getId()!=null){
            this.smtProductMapper.updateByPrimaryKeyWithBLOBs(smtProduct);
        }else{
            this.smtProductMapper.insertSelective(smtProduct);
        }
    }

    @Override
    public List<SmtProductQuery> selectSmtProductQueryList(Map map, Page page){
        return this.smtProductQueryMapper.selectSmtProductQueryList(map,page);
    }

    @Override
    public SmtProduct selectById(long id){
        return this.smtProductMapper.selectByPrimaryKey(id);
    }
}
