package com.joyintech.yuntai.module.cfg.service.moduleinfo;

import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.enums.CfgSystemFieldEnum;
import com.joyintech.yuntai.module.cfg.service.dbsystemcolumn.DbSystemColumnService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/30
 */
@Component
public class DbSystemFieldConfig {
    @Resource
    private DbSystemColumnService systemColumnService;
    @Getter
    @Value("${mybatis-plus.global-config.db-config.logic-delete-field:deleted}")
    private String deleteField;
    @Getter
    @Value("${mybatis-plus.global-config.db-config.logic-delete-value:1}")
    private String deleteValue;
    @Getter
    @Value("${mybatis-plus.global-config.db-config.logic-not-delete-value:0}")
    private String notDeleteVaule;

    @Value("${mybatis-plus.global-config.db-config.logic-delete-field-type:number}")
    private String deleteFieldType;

    private static Map<Long,List<DbSystemColumnDO>> systemFieldMap = new HashMap<>();


    /**
     * 获取系统字段
     * @param dataSourceId 数据源id
     */
    public List<DbSystemColumnDO> getSystemFieldList(Long dataSourceId) {
        if (!systemFieldMap.containsKey(dataSourceId)) {
            systemFieldMap.put(dataSourceId,systemColumnService.list(dataSourceId));
        }
        return systemFieldMap.get(dataSourceId);
    }

    public Object getRealDeleteValue() {
        if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(this.deleteFieldType)) {
            return Integer.parseInt(this.deleteValue);
        }else {
            return this.deleteValue;
        }
    }

    public Object getRealNotDeleteValue() {
        if (CfgSystemFieldEnum.NUMBER.getCode().equalsIgnoreCase(this.deleteFieldType)) {
            return Integer.parseInt(this.notDeleteVaule);
        }else {
            return this.notDeleteVaule;
        }
    }

}
