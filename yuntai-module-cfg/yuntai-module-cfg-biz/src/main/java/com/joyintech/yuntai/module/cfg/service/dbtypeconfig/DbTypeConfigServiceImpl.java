package com.joyintech.yuntai.module.cfg.service.dbtypeconfig;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.joyintech.yuntai.module.cfg.controller.admin.columndefinition.vo.ColumnTypeVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain.DbDataDomainDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbtypeconfig.DbTypeConfigDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.dbdatadomain.DbDataDomainMapper;
import com.joyintech.yuntai.module.cfg.dal.mysql.dbtypeconfig.DbTypeConfigMapper;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGeneratorFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据库字段映射表,默认从mysql映射到其他数据库 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DbTypeConfigServiceImpl implements DbTypeConfigService {
    @Resource
    private DbTypeConfigMapper dbTypeConfigMapper;

    @Resource
    private DbDataDomainMapper dbDataDomainMapper;


    @Override
    public List<ColumnTypeVO> getColumnTypeList(Long dataSourceId) {
        List<ColumnTypeVO> columnTypeList = new ArrayList<>();
        String dbType = TableStatementGeneratorFactory.getDbType(dataSourceId).getDb();
        List<DbDataDomainDO> doList = dbDataDomainMapper.selectList();
        if (dbType.equalsIgnoreCase(DbType.MYSQL.getDb())) {
            List<DbTypeConfigDO> typeList = dbTypeConfigMapper.selectListByTypeSource(dbType);
            Map<String, List<DbTypeConfigDO>> typeMap = typeList.stream().collect(Collectors.groupingBy(DbTypeConfigDO::getColumnType));
            Set<String> existType = new HashSet<>();
            doList.forEach(item -> {
                ColumnTypeVO vo = new ColumnTypeVO();
                vo.setColumnTypeWithId(item.getId() + ":" + item.getDbType());
                vo.setDataDomainId(item.getId());
                vo.setColumnType(item.getDbType());
                vo.setColumnTypeName(item.getDataType());
                vo.setTypeSource(dbType);
                vo.setColumnLength(item.getDataLength());
                vo.setColumnScale(item.getDataScale());
                if (typeMap.get(item.getDbType()) != null) {
                    vo.setInputRule(typeMap.get(item.getDbType()).get(0).getInputRule());
                }
                existType.add(item.getDbType());
                columnTypeList.add(vo);
            });
            typeList.forEach(item -> {
                //if (!existType.contains(item.getColumnType())) {
                    ColumnTypeVO vo = new ColumnTypeVO();
                    vo.setColumnTypeWithId(item.getColumnType());
                    vo.setColumnType(item.getColumnType());
                    vo.setColumnTypeName(item.getColumnType());
                    vo.setTypeSource(dbType);
                    vo.setInputRule(item.getInputRule());
                    columnTypeList.add(vo);
                //}
            });
        } else {
            List<DbTypeConfigDO> typeList = dbTypeConfigMapper.selectList(DbTypeConfigDO::getTargetTypeSource, dbType);
            Map<String, List<DbTypeConfigDO>> typeMap = typeList.stream().collect(Collectors.groupingBy(DbTypeConfigDO::getColumnType));
            Set<String> existType = new HashSet<>();
            doList.forEach(item -> {
                ColumnTypeVO vo = new ColumnTypeVO();
                List<DbTypeConfigDO> colList = typeMap.get(item.getDbType());
                if (CollUtil.isNotEmpty(colList)) {
                    vo.setColumnType(colList.get(0).getTargetColumnType());
                    vo.setInputRule(colList.get(0).getInputRule());
                } else {
                    vo.setColumnType(item.getDbType());
                }
                vo.setColumnTypeWithId(item.getId() + ":" + vo.getColumnType());
                vo.setDataDomainId(item.getId());
                vo.setColumnTypeName(item.getDataType());
                vo.setTypeSource(dbType);
                vo.setColumnLength(item.getDataLength());
                vo.setColumnScale(item.getDataScale());
                existType.add(item.getDbType());
                columnTypeList.add(vo);
            });
            typeList.forEach(item -> {
                if (!existType.contains(item.getTargetColumnType())) {
                    ColumnTypeVO vo = new ColumnTypeVO();
                    vo.setColumnType(item.getTargetColumnType());
                    vo.setColumnTypeName(item.getTargetColumnType());
                    vo.setColumnTypeWithId(vo.getColumnType());
                    vo.setTypeSource(dbType);
                    vo.setInputRule(item.getInputRule());
                    existType.add(item.getTargetColumnType());
                    columnTypeList.add(vo);
                }
            });
        }
        return columnTypeList;
    }
}
