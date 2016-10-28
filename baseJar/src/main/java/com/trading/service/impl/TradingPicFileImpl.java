package com.trading.service.impl;

import com.base.database.trading.mapper.TradingAddItemMapper;
import com.base.database.trading.mapper.TradingPicFileMapper;
import com.base.database.trading.model.TradingAddItem;
import com.base.database.trading.model.TradingAddItemExample;
import com.base.database.trading.model.TradingPicFile;
import com.base.database.trading.model.TradingPicFileExample;
import com.base.utils.common.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/14.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingPicFileImpl implements com.trading.service.ITradingPicFile {
    @Autowired
    private TradingPicFileMapper tradingPicFileMapper;

    @Override
    public void saveTradingPicFile(TradingPicFile tradingPicFile){
        if(tradingPicFile.getId()!=null){
            this.tradingPicFileMapper.updateByPrimaryKeySelective(tradingPicFile);
        }else{
            this.tradingPicFileMapper.insertSelective(tradingPicFile);
        }
    }

    @Override
    public TradingPicFile selectByMd5id(String mdk5id){
        TradingPicFileExample tpf = new TradingPicFileExample();
        tpf.createCriteria().andMd5StrEqualTo(mdk5id);
        List<TradingPicFile> li = this.tradingPicFileMapper.selectByExample(tpf);
        if(li!=null&&li.size()>0){
            return li.get(0);
        }else{
            return null;
        }
    }


}
