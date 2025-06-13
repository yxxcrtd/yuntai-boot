package com.joyintech.yuntai.module.infra.controller.admin.config;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.infra.controller.admin.config.vo.*;
import com.joyintech.yuntai.module.infra.convert.config.ConfigConvert;
import com.joyintech.yuntai.module.infra.dal.dataobject.config.ConfigDO;
import com.joyintech.yuntai.module.infra.enums.ErrorCodeConstants;
import com.joyintech.yuntai.module.infra.enums.config.ConfigTypeEnum;
import com.joyintech.yuntai.module.infra.service.config.ConfigService;
import com.joyintech.yuntai.module.infra.service.config.ConfigServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 参数配置")
@RestController
@RequestMapping("/infra/config")
@Validated
public class ConfigController {

    @Resource
    private ConfigService configService;

    @PostMapping("/create")
    @Operation(summary = "创建参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:create')")
    public CommonResult<Long> createConfig(@Valid @RequestBody ConfigSaveReqVO createReqVO) {
        return success(configService.createConfig(createReqVO));
    }
    @PostMapping("/param/create")
    @Operation(summary = "创建系统参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:create')")
    public CommonResult<Long> createParamConfig(@Valid @RequestBody ConfigSaveReqVO createReqVO) {
        createReqVO.setCategory(ConfigServiceImpl.CATEGORY_PARAM);
        createReqVO.setType(ConfigTypeEnum.CUSTOM.getType());
        return success(configService.createConfig(createReqVO));
    }

    @PostMapping("/base/create")
    @Operation(summary = "创建基础配置")
    @PreAuthorize("@ss.hasPermission('infra:config:create')")
    public CommonResult<Long> createBaseConfig(@Valid @RequestBody BaseConfigSaveReqVO createReqVO) {
        return success(configService.createBaseConfig(createReqVO));
    }

    @PostMapping("/security/create")
    @Operation(summary = "创建安全配置")
    @PreAuthorize("@ss.hasPermission('infra:config:create')")
    public CommonResult<Long> createSecurityConfig(@Valid @RequestBody BaseConfigSaveReqVO createReqVO) {
        return success(configService.createSecurityConfig(createReqVO));
    }

    @GetMapping("/base/get")
    @Operation(summary = "获取基础参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<BaseConfigSaveReqVO> getBaseConfig() {
        return success(configService.getBaseConfig());
    }

    @GetMapping("/security/get")
    @Operation(summary = "获取安全配置")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<BaseConfigSaveReqVO> getSecurityConfig() {
        return success(configService.getSecurityConfig());
    }

    @PutMapping("/update")
    @Operation(summary = "修改参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:update')")
    public CommonResult<Boolean> updateConfig(@Valid @RequestBody ConfigSaveReqVO updateReqVO) {
        configService.updateConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除参数配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:config:delete')")
    public CommonResult<Boolean> deleteConfig(@RequestParam("id") Long id) {
        configService.deleteConfig(id);
        return success(true);
    }

    @PostMapping("/save")
    @Operation(summary = "保存参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:save')")
    public CommonResult<Boolean> createConfig(@Valid @RequestBody List<ConfigSaveReqVO> reqVOList) {
        configService.saveConfig(reqVOList);
        return success(true);
    }

    @GetMapping(value = "/get-by-category")
    @Operation(summary = "获得分组下参数配置")
    @Parameter(name = "category", description = "分组", required = true, example = "系统基本配置")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<List<ConfigRespVO>> getConfig(@RequestParam("category") String category) {
        return success(ConfigConvert.INSTANCE.convertList(configService.getConfig(category)));
    }

    @GetMapping(value = "/get")
    @Operation(summary = "获得参数配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<ConfigRespVO> getConfig(@RequestParam("id") Long id) {
        return success(ConfigConvert.INSTANCE.convert(configService.getConfig(id)));
    }

    @GetMapping(value = "/get-value-by-key")
    @Operation(summary = "根据参数键名查询参数值", description = "不可见的配置，不允许返回给前端")
    @Parameter(name = "key", description = "参数键", required = true, example = "yunai.biz.username")
    public CommonResult<String> getConfigKey(@RequestParam("key") String key) {
        ConfigDO config = configService.getConfigByKey(key);
        if (config == null) {
            return success(null);
        }
        if (!config.getVisible()) {
            throw exception(ErrorCodeConstants.CONFIG_GET_VALUE_ERROR_IF_VISIBLE);
        }
        return success(config.getValue());
    }

    @GetMapping("/page")
    @Operation(summary = "获取参数配置分页")
    @PreAuthorize("@ss.hasPermission('infra:config:query')")
    public CommonResult<PageResult<ConfigRespVO>> getConfigPage(@Valid ConfigPageReqVO pageReqVO) {
        PageResult<ConfigDO> page = configService.getConfigPage(pageReqVO);
        return success(ConfigConvert.INSTANCE.convertPage(page));
    }

    @GetMapping("/export")
    @Operation(summary = "导出参数配置")
    @PreAuthorize("@ss.hasPermission('infra:config:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportConfig(ConfigPageReqVO exportReqVO,
                             HttpServletResponse response) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ConfigDO> list = configService.getConfigPage(exportReqVO).getList();
        // 输出
        ExcelUtils.write(response, "参数配置.xls", "数据", ConfigRespVO.class,
                ConfigConvert.INSTANCE.convertList(list));
    }

}
