package com.joyintech.yuntai.module.cfg.service.dbsystemcolumn;

import javax.validation.Valid;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.DbSystemColumnPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.DbSystemColumnSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;

import java.util.List;

/**
 * 数据库系统字段 Service 接口
 *
 * @author 兆尹云台
 */
public interface DbSystemColumnService {

    /**
     * 创建数据库系统字段
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDbSystemColumn(@Valid DbSystemColumnSaveReqVO createReqVO);

    /**
     * 更新数据库系统字段
     *
     * @param updateReqVO 更新信息
     */
    void updateDbSystemColumn(@Valid DbSystemColumnSaveReqVO updateReqVO);

    /**
     * 删除数据库系统字段
     *
     * @param id 编号
     */
    void deleteDbSystemColumn(Long id);

    /**
     * 获得数据库系统字段
     *
     * @param id 编号
     * @return 数据库系统字段
     */
    DbSystemColumnDO getDbSystemColumn(Long id);

    /**
     * 获得数据库系统字段分页
     *
     * @param pageReqVO 分页查询
     * @return 数据库系统字段分页
     */
    PageResult<DbSystemColumnDO> getDbSystemColumnPage(DbSystemColumnPageReqVO pageReqVO);

    /**
     * 获取数据源对应的默认系统字段
     * @param dataSourceId
     * @return
     */
    List<DbSystemColumnDO> list(Long dataSourceId);

}
