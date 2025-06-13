package com.joyintech.yuntai.module.cfg.openapi;

import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.module.cfg.openapi.dto.NoticeTypeSaveReqVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataBeftInfoResVO;
import com.joyintech.yuntai.module.cfg.openapi.dto.TADataInvPropertyResVO;
import com.joyintech.yuntai.module.cfg.service.openapi.TADataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取TA视图数据
 *
 * @author haozhenzhen
 * @since 2025-05-12
 */

@Tag(name = "外部调用接口-获取TA数据")
@RestController
@RequestMapping("/admin-api/api/getTAData")
@Validated
@Slf4j
public class TADataController {

    @Resource
    private TADataService taDataService;

    @PostMapping("/getTADataInvProperty")
    @Operation(summary = "获取初始委托人及其财产信息")
    public CommonResult<List<TADataInvPropertyResVO>> getTADataInvProperty (@RequestBody TADataInvPropertyResVO taDataInvPropertyResVO) {
        // 判断当项目code为空时，返回空数据
        if (StringUtil.isEmpty(taDataInvPropertyResVO.getFundCode())) {
            return CommonResult.success(new ArrayList<>());
        }
        List<TADataInvPropertyResVO> list = taDataService.getTADataInvProperty(taDataInvPropertyResVO);
        return CommonResult.success(list);
    }

    @PostMapping("/getTADataBeftInfo")
    @Operation(summary = "获取初始受益权信息")
    public CommonResult<List<TADataBeftInfoResVO>> getTADataBeftInfo (@RequestBody TADataInvPropertyResVO taDataInvPropertyResVO) {
        // 判断当项目code为空时，返回空数据
        if (StringUtil.isEmpty(taDataInvPropertyResVO.getFundCode())) {
            return CommonResult.success(new ArrayList<>());
        }
        List<TADataBeftInfoResVO> list = taDataService.getTADataBeftInfo(taDataInvPropertyResVO);
        return CommonResult.success(list);
    }


}
