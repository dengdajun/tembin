package com.base.database.customtrading.mapper;

import com.base.domains.querypojos.PaypalQuery;
import com.base.domains.querypojos.PublicUserConfigQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

public interface PublicUserConfigQueryMapper {

    /**
     *
     * @param map
     * @return
     */
    List<PublicUserConfigQuery> selectByPublicUserConfigQueryList(Map map);
}