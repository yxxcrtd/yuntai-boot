package com.joyintech.yuntai.module.cfg.service.openapi;


import com.joyintech.yuntai.module.cfg.dal.mysql.openapi.TADataMapper;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataBeftInfoResVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataInvPropertyResVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TADataServiceImpl implements TADataService {

    @Resource
    private TADataMapper taDataMapper;

    @Override
    public List<TADataInvPropertyResVO> getTADataInvProperty(TADataInvPropertyResVO taDataInvPropertyResVO) {
        return taDataMapper.getTADataInvProperty(taDataInvPropertyResVO.getFundCode());
    }

    @Override
    public List<TADataBeftInfoResVO> getTADataBeftInfo(TADataInvPropertyResVO taDataInvPropertyResVO) {
        return taDataMapper.getTADataBeftInfo(taDataInvPropertyResVO.getFundCode());
    }
}
