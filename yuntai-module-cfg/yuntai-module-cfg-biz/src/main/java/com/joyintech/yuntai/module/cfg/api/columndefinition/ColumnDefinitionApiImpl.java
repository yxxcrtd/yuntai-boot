package com.joyintech.yuntai.module.cfg.api.columndefinition;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.columndefinition.ColumnDefinitionApi;
import com.joyintech.yuntai.module.cfg.columndefinition.dto.ColumnDefinitionDTO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import com.joyintech.yuntai.module.cfg.service.moduleinfo.DbSystemFieldConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @Author Administrator
 * @Date 2024/10/31
 */
@Service
public class ColumnDefinitionApiImpl implements ColumnDefinitionApi {
    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;
    @Resource
    private DbSystemFieldConfig systemFieldConfig;

    @Override
    public void insertColumn(Long dataSourceId,List<ColumnDefinitionDTO> list) {
        List<DbSystemColumnDO> systemList = systemFieldConfig.getSystemFieldList(dataSourceId);
        if (CollUtil.isNotEmpty(systemList)) {
            Map<String,DbSystemColumnDO> map = systemList.stream().collect(Collectors.toMap(DbSystemColumnDO::getColumnName, item -> item));
            list.forEach(item -> {
                if (map.containsKey(item.getColumnName())){
                    item.setIsSys(true);
                }
            });
        }
        columnDefinitionMapper.insertBatch(BeanUtils.toBean(list, ColumnDefinitionDO.class));
    }
}
