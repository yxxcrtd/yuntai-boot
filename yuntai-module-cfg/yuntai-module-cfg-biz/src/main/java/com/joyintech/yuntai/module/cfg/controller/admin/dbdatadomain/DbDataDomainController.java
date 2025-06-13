package com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainPageReqVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainRespVO;
import com.joyintech.yuntai.module.cfg.controller.admin.dbdatadomain.vo.DbDataDomainSaveReqVO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbdatadomain.DbDataDomainDO;
import com.joyintech.yuntai.module.cfg.service.dbdatadomain.DbDataDomainService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 数据库字段类型表(数据域)")
@RestController
@RequestMapping("/cfg/db-data-domain")
@Validated
public class DbDataDomainController {

    @Resource
    private DbDataDomainService dbDataDomainService;

    @PostMapping("/create")
    @Operation(summary = "创建数据库字段类型表(数据域)")
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:create')")
    public CommonResult<Long> createDbDataDomain(@Valid @RequestBody DbDataDomainSaveReqVO createReqVO) {
        return success(dbDataDomainService.createDbDataDomain(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据库字段类型表(数据域)")
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:update')")
    public CommonResult<Boolean> updateDbDataDomain(@Valid @RequestBody DbDataDomainSaveReqVO updateReqVO) {
        dbDataDomainService.updateDbDataDomain(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除数据库字段类型表(数据域)")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:delete')")
    public CommonResult<Boolean> deleteDbDataDomain(@RequestParam("id") Long id) {
        dbDataDomainService.deleteDbDataDomain(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据库字段类型表(数据域)")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:query')")
    public CommonResult<DbDataDomainRespVO> getDbDataDomain(@RequestParam("id") Long id) {
        DbDataDomainDO dbDataDomain = dbDataDomainService.getDbDataDomain(id);
        return success(BeanUtils.toBean(dbDataDomain, DbDataDomainRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据库字段类型表(数据域)分页")
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:query')")
    public CommonResult<PageResult<DbDataDomainRespVO>> getDbDataDomainPage(@Valid DbDataDomainPageReqVO pageReqVO) {
        PageResult<DbDataDomainDO> pageResult = dbDataDomainService.getDbDataDomainPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DbDataDomainRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据库字段类型表(数据域) Excel")
    @PreAuthorize("@ss.hasPermission('cfg:db-data-domain:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDbDataDomainExcel(@Valid DbDataDomainPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DbDataDomainDO> list = dbDataDomainService.getDbDataDomainPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据库字段类型表(数据域).xls", "数据", DbDataDomainRespVO.class,
                        BeanUtils.toBean(list, DbDataDomainRespVO.class));
    }

}