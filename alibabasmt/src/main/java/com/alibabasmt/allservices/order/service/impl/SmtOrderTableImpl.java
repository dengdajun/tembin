package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibabasmt.allservices.datadic.service.ISmtDataDictionary;
import com.alibabasmt.allservices.order.service.ISmtOrderBuyerInfo;
import com.alibabasmt.allservices.order.service.ISmtOrderChildrenOrder;
import com.alibabasmt.allservices.order.service.ISmtOrderDetails;
import com.alibabasmt.allservices.order.service.ISmtOrderTable;
import com.alibabasmt.database.customsmt.mapper.OrderTableMapper;
import com.alibabasmt.database.datadic.model.SmtDataDictionary;
import com.alibabasmt.database.smtorder.mapper.SmtOrderTableMapper;
import com.alibabasmt.database.smtorder.model.*;
import com.alibabasmt.domains.querypojos.smtorder.OrderTableQuery;
import com.base.mybatis.page.Page;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.*;

/**
 * Created by Administrtor on 2015/3/20.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderTableImpl implements ISmtOrderTable {

    @Autowired
    private SmtOrderTableMapper smtOrderTableMapper;
    @Autowired
    private ISmtOrderChildrenOrder iSmtOrderChildrenOrder;
    @Autowired
    private OrderTableMapper orderTableMapper;
    @Autowired
    private ISmtOrderBuyerInfo iSmtOrderBuyerInfo;
    @Autowired
    private ISmtOrderDetails iSmtOrderDetails;
    @Autowired
    private ISmtDataDictionary iSmtDataDictionary;

    @Override
    public void saveSmtOrderTable(SmtOrderTable smtOrderTable) throws Exception {
        if(smtOrderTable.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderTable);
            smtOrderTableMapper.insert(smtOrderTable);
        }else{
            SmtOrderTable t=smtOrderTableMapper.selectByPrimaryKey(smtOrderTable.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderTableMapper.class,smtOrderTable.getId(),"Synchronize");
            smtOrderTableMapper.updateByPrimaryKeySelective(smtOrderTable);
        }
    }

    @Override
    public SmtOrderTable selectSmtOrderTableByOrderId(String orderId) {
        SmtOrderTableExample example=new SmtOrderTableExample();
        SmtOrderTableExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<SmtOrderTable> list=smtOrderTableMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }

    @Override
    public List<SmtOrderTable>  parseSMTOrderAndSaveOrder(String result,Long smtAcountId) throws Exception {
        List<SmtOrderTable> list=new ArrayList<>();
        Map jsons = JSON.parseObject(result, HashMap.class);
        JSONArray orderList= (JSONArray) jsons.get("orderList");
        for(int i=0;i<orderList.size();i++){
            SmtOrderTable orderTable=new SmtOrderTable();
            Map<String,Object> orderMap= (Map<String, Object>) orderList.get(i);
            Long orderId= (Long) orderMap.get("orderId");
            orderTable.setBiztype((String) orderMap.get("bizType"));
            String gmtCreate=(String)orderMap.get("gmtCreate");
            String gmtModified=(String)orderMap.get("gmtModified");
            orderTable.setGmtcreate(DateUtils.StringToDate(gmtCreate));
            orderTable.setGmtmodified(DateUtils.StringToDate(gmtModified));
            orderTable.setMemo((String)orderMap.get("memo"));
            orderTable.setOrderid(orderId+"");
            orderTable.setOrderstatus((String)orderMap.get("orderStatus"));
            JSONArray productList= (JSONArray) orderMap.get("productList");
            SmtOrderTable s1=selectSmtOrderTableByOrderId(orderId+"");
            if(s1!=null){
                orderTable.setId(s1.getId());
            }
            orderTable.setUpdateTime(new Date());
            orderTable.setSmtAcountId(smtAcountId);
            saveSmtOrderTable(orderTable);
            list.add(orderTable);
            for(int j=0;j<productList.size();j++){
                SmtOrderChildrenOrder childOrder=new SmtOrderChildrenOrder();
                Map<String,Object> childMap= (Map<String, Object>) productList.get(j);
                Long childId= (Long) childMap.get("childId");
                childOrder.setChildid(childId+"");
                childOrder.setGoodspreparetime(childMap.get("goodsPrepareTime")+"");
                Boolean moneyBack3x= (Boolean) childMap.get("moneyBack3x");
                if(moneyBack3x){
                    childOrder.setMoneyback3x("true");
                }else{
                    childOrder.setMoneyback3x("false");
                }
                childOrder.setParentorderid(orderId+"");
                childOrder.setProductcount((Integer) childMap.get("productCount")+"");
                childOrder.setProductid((Long) childMap.get("productId")+"");
                childOrder.setProductimgurl((String) childMap.get("productImgUrl"));
                childOrder.setProductname((String) childMap.get("productName"));
                childOrder.setProductsnapurl((String) childMap.get("productSnapUrl"));
                childOrder.setProductunit((String) childMap.get("productUnit"));
                childOrder.setProductunitprice((String) childMap.get("productUnitPrice"));
                childOrder.setSonorderstatus((String) childMap.get("sonOrderStatus"));
                childOrder.setProductunitpricecur((String) childMap.get("productUnitPriceCur"));
                childOrder.setSkucode((String) childMap.get("skuCode"));
                SmtOrderChildrenOrder s2=iSmtOrderChildrenOrder.selectSmtOrderChildrenOrderByChildId(childId+"");
                if(s2!=null){
                    childOrder.setId(s2.getId());
                }
                childOrder.setUpdateTime(new Date());
                iSmtOrderChildrenOrder.saveSmtOrderChildrenOrder(childOrder);
            }
        }
        return list;
    }

    @Override
    public List<OrderTableQuery> selectSmtOrderTableList(Map map, Page page) {
        return orderTableMapper.selectSmtOrderTableList(map,page);
    }

    @Override
    public SmtOrderTable selectSmtOrderTableById(Long id) {
        return smtOrderTableMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmtOrderTable> selectSmtOrderTableByStatusOrFolder(String status, String folderId, List<Long> ebays) {
        SmtOrderTableExample example=new SmtOrderTableExample();
        SmtOrderTableExample.Criteria cr=example.createCriteria();
        if(StringUtils.isNotBlank(status)){
            cr.andOrderstatusEqualTo(status);
        }
        if(StringUtils.isNotBlank(folderId)){
            cr.andFolderEqualTo(folderId);
        }
        cr.andCreateUserIn(ebays);
        List<SmtOrderTable> list=smtOrderTableMapper.selectByExample(example);
        return list;
    }

    @Override
    public void downloadOrders(List<SmtOrderTable> list, String outputFile, HttpServletResponse response) throws Exception {
        // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 在Excel 工作簿中建一工作表
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        // 设置单元格格式(文本)
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));

        // 在索引0的位置创建行（第一行）
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell1 = row.createCell(0);// 第一列
        HSSFCell cell2 = row.createCell(1);
        HSSFCell cell3 = row.createCell(2);
        HSSFCell cell4 = row.createCell(3);
        HSSFCell cell5 = row.createCell(4);
        HSSFCell cell6= row.createCell(5);
        HSSFCell cell7 = row.createCell(6);
        HSSFCell cell8 = row.createCell(7);
        HSSFCell cell9 = row.createCell(8);
        HSSFCell cell10 = row.createCell(9);
        HSSFCell cell11 = row.createCell(10);
        HSSFCell cell12= row.createCell(11);
        // 定义单元格为字符串类型
        cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
        // 在单元格中输入头数据
        cell1.setCellValue("订单号 / 源订单号");
        cell2.setCellValue("买家ebay / 买家邮箱");
        cell3.setCellValue("追踪号 / 运送商");
        cell4.setCellValue("title");
        cell5.setCellValue("productId");
        cell6.setCellValue("售价");
        cell7.setCellValue("售出日期");
        cell8.setCellValue("发货日期");
        cell9.setCellValue("支付日期");
        cell10.setCellValue("订单状态");
        for(int i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            String[] cells=new String[12];
            SmtOrderDetails detail=iSmtOrderDetails.selectSmtOrderDetailsByOrderId(list.get(i).getOrderid());
            cells[0]=list.get(i).getOrderid();
            SmtOrderBuyerInfo buyerInfo= iSmtOrderBuyerInfo.selectSmtOrderBuyerInfoByLoginId(detail.getBuyerloginid());
            cells[1]=detail.getBuyersignerfullname()+"("+buyerInfo.getEmail()+")";
            List<SmtOrderChildrenOrder> childrenOrder=iSmtOrderChildrenOrder.selectSmtOrderChildrenOrderByParentId(list.get(i).getOrderid());
            cells[2]=detail.getLogisticsno()+"/"+detail.getLogisticsservicename();
            if(childrenOrder!=null&&childrenOrder.size()>0){
                String productName="";
                String productid="";
                String productunitprice="";
                for(SmtOrderChildrenOrder child:childrenOrder){
                    productName+=child.getProductname()+"/";
                    productid+=child.getProductid()+"/";
                    productunitprice+=child.getProductunitprice()+"/";
                }
                cells[3]=productName.substring(0,productName.length()-1);
                cells[4]=productid.substring(0,productid.length()-1);
                cells[5]=productunitprice.substring(0,productunitprice.length()-1);
            }
            if(list.get(i).getGmtcreate()!=null){
                cells[6]= String.valueOf(list.get(i).getGmtcreate());
            }
            if(String.valueOf(detail.getGmtsend())!=null){
                cells[7]=String.valueOf(detail.getGmtsend());
            }
            if(String.valueOf(detail.getGmtpaysuccess())!=null){
                cells[8]= String.valueOf(detail.getGmtpaysuccess());
            }
            SmtDataDictionary dataDictionary= iSmtDataDictionary.queryByTypeAndName("orderStatus", list.get(i).getOrderstatus());
            cells[9]=dataDictionary.getValue();
            for(int j=0;j<cells.length;j++){
                HSSFCell cell = row.createCell(j);// 第一列
                // 设置单元格格式
                cell.setCellStyle(cellStyle);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(cells[j]);
            }
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        workbook.write(baos);
        ServletOutputStream os=response.getOutputStream();
        os.write(baos.toByteArray());
        os.flush();
        os.close();
    }

}
