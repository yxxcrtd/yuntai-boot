package com.joyintech.yuntai.module.cfg.service.dbdatadomain;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.DB_DATA_DOMAIN_NOT_EXISTS;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain.DbDataDomainDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.dbdatadomain.DbDataDomainMapper;

/**
 * 数据库字段类型表(数据域) Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DbDataDomainServiceImpl implements DbDataDomainService {

    @Resource
    private DbDataDomainMapper dbDataDomainMapper;

    @Override
    public Long createDbDataDomain(DbDataDomainSaveReqVO createReqVO) {
        // 插入
        DbDataDomainDO dbDataDomain = BeanUtils.toBean(createReqVO, DbDataDomainDO.class);
        dbDataDomainMapper.insert(dbDataDomain);
        // 返回
        return dbDataDomain.getId();
    }

    @Override
    public void updateDbDataDomain(DbDataDomainSaveReqVO updateReqVO) {
        // 校验存在
        validateDbDataDomainExists(updateReqVO.getId());
        // 更新
        DbDataDomainDO updateObj = BeanUtils.toBean(updateReqVO, DbDataDomainDO.class);
        dbDataDomainMapper.updateById(updateObj);
    }

    @Override
    public void deleteDbDataDomain(Long id) {
        // 校验存在
        validateDbDataDomainExists(id);
        // 删除
        dbDataDomainMapper.deleteById(id);
    }

    private void validateDbDataDomainExists(Long id) {
        if (dbDataDomainMapper.selectById(id) == null) {
            throw exception(DB_DATA_DOMAIN_NOT_EXISTS);
        }
    }

    @Override
    public DbDataDomainDO getDbDataDomain(Long id) {
        return dbDataDomainMapper.selectById(id);
    }

    @Override
    public PageResult<DbDataDomainDO> getDbDataDomainPage(DbDataDomainPageReqVO pageReqVO) {
        return dbDataDomainMapper.selectPage(pageReqVO);
    }

}