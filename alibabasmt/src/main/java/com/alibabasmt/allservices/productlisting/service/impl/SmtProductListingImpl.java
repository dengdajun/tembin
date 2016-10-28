package com.alibabasmt.allservices.productlisting.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibabasmt.database.customsmt.mapper.SmtProductListingQueryMapper;
import com.alibabasmt.database.smtproduct.mapper.SmtProductListingMapper;
import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.alibabasmt.database.smtproduct.model.SmtProductListingExample;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.mybatis.page.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrtor on 2015/3/31.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtProductListingImpl implements com.alibabasmt.allservices.productlisting.service.ISmtProductListing {
    static Logger logger = Logger.getLogger(SmtProductListingImpl.class);
    @Autowired
    private SmtProductListingMapper smtProductListingMapper;
    @Autowired
    private SmtProductListingQueryMapper smtProductListingQueryMapper;

    @Override
    public void saveSmtProductListing(SmtProductListing smtProductListing){
        if(smtProductListing.getId()!=null){
            this.smtProductListingMapper.updateByPrimaryKeyWithBLOBs(smtProductListing);
        }else{
            this.smtProductListingMapper.insertSelective(smtProductListing);
        }
    }

    @Override
    public void saveSmtProductListingList(List<SmtProductListing> list){
        for(SmtProductListing spl:list){
            this.saveSmtProductListing(spl);
        }
    }

    @Override
    public Map queryProductListJson(long smtAccount, int pageSize, int currentPage, String type){
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccount);
        apiCallVO.setUrlPath(APIStaticParm.findProductInfoListQuery);
        Map<String,String> m=new HashMap<String, String>();
        m.put("access_token", "");//access_token就设置为空，可以添加其他参数
        m.put("productStatusType", type);
        m.put("pageSize",pageSize+"");
        m.put("currentPage",currentPage+"");
        apiCallVO.setParam(m);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        Map jsons = JSON.parseObject(result, HashMap.class);
        if(jsons==null){
            return jsons;
        }
        if ("true".equals(jsons.get("success")+"")){
            return jsons;
        }else{
            logger.error("查询产品列表未获取对应数据报错！");
            return null;
        }
    }

    @Override
    public void taskProductList(long smtAccountId, String type){
        List<SmtProductListing> lis = new ArrayList<SmtProductListing>();
        Map map = this.queryProductListJson(smtAccountId,100,1,type);
        Map m=null;
        if(map!=null){
            int totalPage = Integer.parseInt(map.get("totalPage") + "");
            for(int i=1;i<=totalPage;i++){
                if(i==1){
                    m=map;
                }else{
                    m=this.queryProductListJson(smtAccountId,100,i,type);
                }
                if(m!=null){
                    List<Map> lim = (List<Map>)m.get("aeopAEProductDisplayDTOList");
                    if(lim!=null&&lim.size()>0){
                        for(Map ms:lim){
                            long productId = Long.parseLong(ms.get("productId") + "");
                            SmtProductListing spl = this.selectByProductId(productId);
                            if(spl==null){
                                spl = new SmtProductListing();
                                spl.setCreateTime(new Date());
                            }
                            spl.setCreateUser(smtAccountId);
                            spl.setUpdateTime(new Date());
                            spl.setTaskFlag("0");
                            spl.setStatus(type);
                            spl.setGmtmodified(ms.get("gmtModified")+"");
                            spl.setGmtcreate(ms.get("gmtCreate") + "");
                            spl.setFreighttemplateid(Long.parseLong(ms.get("freightTemplateId") + ""));
                            spl.setOwnermemberseq(Long.parseLong(ms.get("ownerMemberSeq") + ""));
                            spl.setSubject(ms.get("subject") + "");
                            spl.setImageurls(ms.get("imageURLs") + "");
                            spl.setOwnermemberid(ms.get("ownerMemberId") + "");
                            spl.setWsofflinedate(ms.get("wsOfflineDate") + "");
                            spl.setProductid(productId);
                            spl.setProductminprice(Double.parseDouble(ms.get("productMinPrice") + ""));
                            spl.setWsdisplay(ms.get("wsDisplay") + "");
                            spl.setProductmaxprice(Double.parseDouble(ms.get("productMaxPrice") + ""));
                            lis.add(spl);
                        }
                    }
                }
            }
        }
        if(lis!=null&&lis.size()>0){
            this.saveSmtProductListingList(lis);
        }
    }

    @Override
    public SmtProductListing selectByProductId(long productId){
        SmtProductListingExample sple = new SmtProductListingExample();
        sple.createCriteria().andProductidEqualTo(productId);
        List<SmtProductListing> list = this.smtProductListingMapper.selectByExample(sple);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<SmtProductListing> selectByProductQueryList(Map map,Page page){
        return this.smtProductListingQueryMapper.selectSmtProductListingQueryList(map,page);
    }

}
