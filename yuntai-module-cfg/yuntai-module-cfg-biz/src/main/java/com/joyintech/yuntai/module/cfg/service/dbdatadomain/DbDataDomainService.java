package com.joyintech.yuntai.module.cfg.service.dbdatadomain;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain.DbDataDomainDO;

/**
 * 数据库字段类型表(数据域) Service 接口
 *
 * @author 兆尹云台
 */
public interface DbDataDomainService {

    /**
     * 创建数据库字段类型表(数据域)
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDbDataDomain(@Valid DbDataDomainSaveReqVO createReqVO);

    /**
     * 更新数据库字段类型表(数据域)
     *
     * @param updateReqVO 更新信息
     */
    void updateDbDataDomain(@Valid DbDataDomainSaveReqVO updateReqVO);

    /**
     * 删除数据库字段类型表(数据域)
     *
     * @param id 编号
     */
    void deleteDbDataDomain(Long id);

    /**
     * 获得数据库字段类型表(数据域)
     *
     * @param id 编号
     * @return 数据库字段类型表(数据域)
     */
    DbDataDomainDO getDbDataDomain(Long id);

    /**
     * 获得数据库字段类型表(数据域)分页
     *
     * @param pageReqVO 分页查询
     * @return 数据库字段类型表(数据域)分页
     */
    PageResult<DbDataDomainDO> getDbDataDomainPage(DbDataDomainPageReqVO pageReqVO);

}