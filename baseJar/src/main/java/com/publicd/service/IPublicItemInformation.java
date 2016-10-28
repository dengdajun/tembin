package com.publicd.service;

import com.base.database.publicd.model.PublicItemInformation;
import com.base.domains.querypojos.ItemInformationQuery;
import com.base.mybatis.page.Page;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface IPublicItemInformation {

    void saveItemInformation(PublicItemInformation ItemInformation) throws Exception;

    List<ItemInformationQuery> selectItemInformation(Map map, Page page);

    void deleteItemInformation (Long id) throws Exception;

    PublicItemInformation selectItemInformationByid(Long id);

    List<ItemInformationQuery> selectItemInformationByType(Map map, Page page);

    PublicItemInformation selectItemInformationBySKU(String sku);

    PublicItemInformation selectItemInformationBySKUAndUserId(String sku,List<Long> ebays);

    void exportItemInformation(List<PublicItemInformation> list,String outputFile,ServletOutputStream outputStream) throws Exception;

    void importItemInformation(InputStream file, String fileName) throws Exception;

    List<ItemInformationQuery> selectItemInformationByTypeIsNull();

    List<ItemInformationQuery> selectItemInformationByOrgId(Map map, Page page);
}
