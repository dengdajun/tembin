package com.common.base.utils;

import com.base.utils.common.DateUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by wula on 2014/6/22.
 * 类型转换器
 */
public class DateEditor extends PropertyEditorSupport {
    static Logger logger = Logger.getLogger(DateEditor.class);
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if ( !StringUtils.hasText(text)) {
            setValue(null);
        }
        else {
            try {
                if (text.contains("-") && text.contains(":")){
                    setValue(DateUtils.str2DateTime(text));
                }else if (text.contains("-") && !text.contains(":")){
                    setValue(DateUtils.str2Date(text));
                }

            } catch (Exception e) {
                logger.error("日期转换出错！"+text+"||",e);
            }
        }
    }
    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {
        return getValue().toString();
    }
}

