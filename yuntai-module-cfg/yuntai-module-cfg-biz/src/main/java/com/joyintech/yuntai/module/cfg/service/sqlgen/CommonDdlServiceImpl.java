package com.joyintech.yuntai.module.cfg.service.sqlgen;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.joyintech.yuntai.module.cfg.dal.sqlgen.CommonDdlMapper;

/**
 * 执行ddl Service 接口
 *
 * @author 兆尹云台
 */
@Service
public class CommonDdlServiceImpl implements CommonDdlService {

    @Resource
    private CommonDdlMapper commonDdlMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void executeSql(List<String> sqlList) {
        sqlList.forEach(sql -> commonDdlMapper.executeSql(sql));
    }
}
