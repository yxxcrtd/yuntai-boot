package com.joyintech.yuntai.module.cfg.service.dbsystemcolumn;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.cfg.enums.ErrorCodeConstants.DB_SYSTEM_COLUMN_NOT_EXISTS;

import javax.annotation.Resource;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.joyintech.yuntai.module.cfg.sqlgen.generator.TableStatementGeneratorFactory;
import com.joyintech.yuntai.module.infra.api.db.DataSourceConfigApi;
import com.joyintech.yuntai.module.infra.api.db.dto.DataSourceConfigRespDTO;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.DbSystemColumnPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.DbSystemColumnSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.dal.mysql.dbsystemcolumn.DbSystemColumnMapper;

import java.util.List;
import java.util.Objects;

/**
 * 数据库系统字段 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DbSystemColumnServiceImpl implements DbSystemColumnService {

    @Resource
    private DbSystemColumnMapper dbSystemColumnMapper;

    @Resource
    private DataSourceConfigApi dataSourceConfigApi;

    @Override
    public Long createDbSystemColumn(DbSystemColumnSaveReqVO createReqVO) {
        // 插入
        DbSystemColumnDO dbSystemColumn = BeanUtils.toBean(createReqVO, DbSystemColumnDO.class);
        dbSystemColumnMapper.insert(dbSystemColumn);
        // 返回
        return dbSystemColumn.getId();
    }

    @Override
    public void updateDbSystemColumn(DbSystemColumnSaveReqVO updateReqVO) {
        // 校验存在
        validateDbSystemColumnExists(updateReqVO.getId());
        // 更新
        DbSystemColumnDO updateObj = BeanUtils.toBean(updateReqVO, DbSystemColumnDO.class);
        dbSystemColumnMapper.updateById(updateObj);
    }

    @Override
    public void deleteDbSystemColumn(Long id) {
        // 校验存在
        validateDbSystemColumnExists(id);
        // 删除
        dbSystemColumnMapper.deleteById(id);
    }

    private void validateDbSystemColumnExists(Long id) {
        if (dbSystemColumnMapper.selectById(id) == null) {
            throw exception(DB_SYSTEM_COLUMN_NOT_EXISTS);
        }
    }

    @Override
    public DbSystemColumnDO getDbSystemColumn(Long id) {
        return dbSystemColumnMapper.selectById(id);
    }

    @Override
    public PageResult<DbSystemColumnDO> getDbSystemColumnPage(DbSystemColumnPageReqVO pageReqVO) {
        if (Objects.nonNull(pageReqVO.getDataSourceId())) {
            pageReqVO.setTypeSource(TableStatementGeneratorFactory.getDbType(pageReqVO.getDataSourceId()).getDb());
        }
        return dbSystemColumnMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DbSystemColumnDO> list(Long dataSourceId) {
        String dbType = null;
        if (Objects.nonNull(dataSourceId)) {
            dbType = TableStatementGeneratorFactory.getDbType(dataSourceId).getDb();
        }else {
            DataSourceConfigRespDTO res = dataSourceConfigApi.getDataSource(DynamicDataSourceContextHolder.peek());
            if(Objects.nonNull(res)) {
                dbType = res.getTypeSource();
            }
        }
        if (StrUtil.isEmpty(dbType)) {
            return Lists.newArrayList();
        }
        return dbSystemColumnMapper.selectList(DbSystemColumnDO::getTypeSource,dbType);
    }

}
