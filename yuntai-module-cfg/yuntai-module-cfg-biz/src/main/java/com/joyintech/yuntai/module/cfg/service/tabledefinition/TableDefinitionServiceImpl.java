package com.joyintech.yuntai.module.cfg.service.tabledefinition;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.TABLE_DEFINITION_NOT_EXISTS;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.joyintech.yuntai.module.cfg.dal.dataobject.columndefinition.ColumnDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.columndefinition.ColumnDefinitionMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.tabledefinition.vo.TableDefinitionSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.tabledefinition.TableDefinitionDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.tabledefinition.TableDefinitionMapper;

import java.util.Collections;
import java.util.List;

/**
 * 表定义 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class TableDefinitionServiceImpl implements TableDefinitionService {

    @Resource
    private TableDefinitionMapper tableDefinitionMapper;
    @Resource
    private ColumnDefinitionMapper columnDefinitionMapper;

    @Override
    public Long createTableDefinition(TableDefinitionSaveReqVO createReqVO) {
        // 插入
        TableDefinitionDO tableDefinition = BeanUtils.toBean(createReqVO, TableDefinitionDO.class);
        tableDefinition.setStatus(false);
        tableDefinitionMapper.insert(tableDefinition);
        // 返回
        return tableDefinition.getId();
    }

    @Override
    public void updateTableDefinition(TableDefinitionSaveReqVO updateReqVO) {
        // 校验存在
        validateTableDefinitionExists(updateReqVO.getId());
        // 更新
        TableDefinitionDO updateObj = BeanUtils.toBean(updateReqVO, TableDefinitionDO.class);
        tableDefinitionMapper.updateById(updateObj);
    }

    @Override
    public void deleteTableDefinition(Long id) {
        // 校验存在
        validateTableDefinitionExists(id);
        // 删除
        tableDefinitionMapper.deletePhysics(id);
        columnDefinitionMapper.deletePhysicsByTableId(id,null);
    }

    private void validateTableDefinitionExists(Long id) {
        if (tableDefinitionMapper.selectById(id) == null) {
            throw exception(TABLE_DEFINITION_NOT_EXISTS);
        }
    }

    @Override
    public TableDefinitionDO getTableDefinition(Long id) {
        return tableDefinitionMapper.selectById(id);
    }

    @Override
    public PageResult<TableDefinitionDO> getTableDefinitionPage(TableDefinitionPageReqVO pageReqVO) {
        return tableDefinitionMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TableDefinitionDO> getTableDefinitionList(TableDefinitionPageReqVO pageReqVO) {
        QueryWrapper<TableDefinitionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(pageReqVO.getDatasourceId() !=null,"datasource_id", pageReqVO.getDatasourceId());
        queryWrapper.eq(pageReqVO.getStatus() !=null,"status", pageReqVO.getStatus());
        return tableDefinitionMapper.selectList(queryWrapper);
    }
}
