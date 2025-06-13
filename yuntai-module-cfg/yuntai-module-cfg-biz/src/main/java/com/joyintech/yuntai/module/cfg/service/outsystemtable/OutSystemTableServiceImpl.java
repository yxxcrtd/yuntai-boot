package com.joyintech.yuntai.module.cfg.service.outsystemtable;

import java.util.ArrayList;
import java.util.List;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.OUT_SYSTEM_TABLE_NOT_EXISTS;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.OutSystemTablePageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.outsystemtable.vo.OutSystemTableSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.outsystemtable.OutSystemTableDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.outsystemtable.OutSystemTableMapper;

/**
 * 外部系统关联 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class OutSystemTableServiceImpl implements OutSystemTableService {

    @Resource
    private OutSystemTableMapper outSystemTableMapper;

    @Override
    public Long createOutSystemTable(OutSystemTableSaveReqVO createReqVO) {
        // 插入
        OutSystemTableDO outSystemTable = BeanUtils.toBean(createReqVO, OutSystemTableDO.class);
        outSystemTableMapper.insert(outSystemTable);
        // 返回
        return outSystemTable.getId();
    }

    @Override
    public void updateOutSystemTable(OutSystemTableSaveReqVO updateReqVO) {
        // 校验存在
        validateOutSystemTableExists(updateReqVO.getId());
        // 更新
        OutSystemTableDO updateObj = BeanUtils.toBean(updateReqVO, OutSystemTableDO.class);
        outSystemTableMapper.updateById(updateObj);
    }

    @Override
    public void deleteOutSystemTable(Long id) {
        // 校验存在
        validateOutSystemTableExists(id);
        // 删除
        outSystemTableMapper.deleteById(id);
    }

    private void validateOutSystemTableExists(Long id) {
        if (outSystemTableMapper.selectById(id) == null) {
            throw exception(OUT_SYSTEM_TABLE_NOT_EXISTS);
        }
    }

    @Override
    public OutSystemTableDO getOutSystemTable(Long id) {
        return outSystemTableMapper.selectById(id);
    }

    @Override
    public PageResult<OutSystemTableDO> getOutSystemTablePage(OutSystemTablePageReqVO pageReqVO) {
        return outSystemTableMapper.selectPage(pageReqVO);
    }

    @Override
    public Long getPageId(Long flowId, Long pageDataId) {
        List<Long> list = new ArrayList<>();
        if(flowId!=null){
            list = outSystemTableMapper.getPageId(flowId.toString());
        }
        if(pageDataId!=null){
            list = outSystemTableMapper.getPageIdByPageDataId(pageDataId);
        }
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

}