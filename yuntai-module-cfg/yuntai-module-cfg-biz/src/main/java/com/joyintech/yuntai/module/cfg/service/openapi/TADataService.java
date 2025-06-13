package com.joyintech.yuntai.module.cfg.service.openapi;

import com.joyintech.yuntai.module.cfg.openapi.dto.TADataBeftInfoResVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataInvPropertyResVO;

import java.util.List;

public interface TADataService {
    List<TADataInvPropertyResVO> getTADataInvProperty(TADataInvPropertyResVO taDataInvPropertyResVO);

    List<TADataBeftInfoResVO> getTADataBeftInfo(TADataInvPropertyResVO taDataInvPropertyResVO);
}
