package com.joyintech.yuntai.module.cfg.service.datasourcechangesql;


import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.joyintech.yuntai.framework.common.util.json.JsonUtils;
import com.joyintech.yuntai.framework.web.core.util.WebFrameworkUtils;
import com.joyintech.yuntai.module.cfg.dal.dataobject.datasourcechangesql.CfgDatasourceChangeSqlDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.datasourcechangesql.CfgDatasourceChangeSqlMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;

@Log4j2
@Service
public class CfgDatasourceChangeSqlService {
    @Resource
    private CfgDatasourceChangeSqlMapper cfgDatasourceChangeSqlMapper;

    public void save(String sql,long orderNo,String tableName) {
        new Thread(() -> {
            CfgDatasourceChangeSqlDO cfgDatasourceChangeSql = new CfgDatasourceChangeSqlDO();
            cfgDatasourceChangeSql.setId(IdWorker.getId());
            cfgDatasourceChangeSql.setSqlContent(sql);
            cfgDatasourceChangeSql.setOrderNo(orderNo);
            cfgDatasourceChangeSql.setTableName(tableName);
            cfgDatasourceChangeSql.setCreator(WebFrameworkUtils.getLoginUserId()+"");
            cfgDatasourceChangeSql.setCreateTime(LocalDateTime.now());
            log.error(JsonUtils.toJsonString(cfgDatasourceChangeSql));
            cfgDatasourceChangeSqlMapper.insert(cfgDatasourceChangeSql);
        }).start();
    }

}
