package com.joyintech.yuntai.module.system.service.dictnew;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;

import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictPageReqVO;
import com.joyintech.yuntai.module.system.controller.admin.dictnew.vo.DictSaveReqVO;
import com.joyintech.yuntai.module.system.dal.dataobject.dictnew.DictDO;
import com.joyintech.yuntai.module.system.dal.mysql.dictnew.DictMapper;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

/**
 * 字典主表 Service 实现类
 *
 * @author 兆尹云台
 */
@Service
@Validated
public class DictServiceImpl implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    public String createDict(DictSaveReqVO createReqVO) {
        // 插入
        DictDO dict = BeanUtils.toBean(createReqVO, DictDO.class);
        dictMapper.insert(dict);
        // 返回
        return dict.getId();
    }

    @Override
    public void updateDict(DictSaveReqVO updateReqVO) {
        // 校验存在
        validateDictExists(updateReqVO.getId());
        // 更新
        DictDO updateObj = BeanUtils.toBean(updateReqVO, DictDO.class);
        dictMapper.updateById(updateObj);
    }

    @Override
    public void deleteDict(String id) {
        // 校验存在
        validateDictExists(id);
        // 删除
        dictMapper.deleteById(id);
    }

    private void validateDictExists(String id) {
        if (dictMapper.selectById(id) == null) {
            throw exception(DICT_NOT_EXISTS);
        }
    }

    @Override
    public DictDO getDict(String id) {
        return dictMapper.selectById(id);
    }

    @Override
    public PageResult<DictDO> getDictPage(DictPageReqVO pageReqVO) {
        return dictMapper.selectPage(pageReqVO);
    }

}