package com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn;

import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.hutool.core.collection.CollUtil;
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

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.joyintech.yuntai.module.cfg.controller.admin.dbsystemcolumn.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.dbsystemcolumn.DbSystemColumnDO;
import com.joyintech.yuntai.module.cfg.service.dbsystemcolumn.DbSystemColumnService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "管理后台 - 数据库系统字段")
@RestController
@RequestMapping("/cfg/db-system-column")
@Validated
public class DbSystemColumnController {

    @Resource
    private DbSystemColumnService dbSystemColumnService;

    @PostMapping("/create")
    @Operation(summary = "创建数据库系统字段")
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:create')")
    public CommonResult<Long> createDbSystemColumn(@Valid @RequestBody DbSystemColumnSaveReqVO createReqVO) {
        return success(dbSystemColumnService.createDbSystemColumn(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新数据库系统字段")
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:update')")
    public CommonResult<Boolean> updateDbSystemColumn(@Valid @RequestBody DbSystemColumnSaveReqVO updateReqVO) {
        dbSystemColumnService.updateDbSystemColumn(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除数据库系统字段")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:delete')")
    public CommonResult<Boolean> deleteDbSystemColumn(@RequestParam("id") Long id) {
        dbSystemColumnService.deleteDbSystemColumn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据库系统字段")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:query')")
    public CommonResult<DbSystemColumnRespVO> getDbSystemColumn(@RequestParam("id") Long id) {
        DbSystemColumnDO dbSystemColumn = dbSystemColumnService.getDbSystemColumn(id);
        return success(BeanUtils.toBean(dbSystemColumn, DbSystemColumnRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据库系统字段分页")
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:query')")
    public CommonResult<PageResult<DbSystemColumnRespVO>> getDbSystemColumnPage(@Valid DbSystemColumnPageReqVO pageReqVO) {
        PageResult<DbSystemColumnDO> pageResult = dbSystemColumnService.getDbSystemColumnPage(pageReqVO);
        PageResult<DbSystemColumnRespVO> result = BeanUtils.toBean(pageResult, DbSystemColumnRespVO.class);
        if (CollUtil.isNotEmpty(pageResult.getList())) {
            result.getList().forEach(item -> {
                if (Objects.nonNull(item.getDataDomainId())) {
                    item.setColumnTypeWithId(String.format("%d:%s", item.getDataDomainId(), item.getColumnType()));
                } else {
                    item.setColumnTypeWithId(item.getColumnType());
                }
                item.setDefaultValue(null);
            });
        }
        return success(result);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据库系统字段 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:db-system-column:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDbSystemColumnExcel(@Valid DbSystemColumnPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DbSystemColumnDO> list = dbSystemColumnService.getDbSystemColumnPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据库系统字段.xls", "数据", DbSystemColumnRespVO.class,
                        BeanUtils.toBean(list, DbSystemColumnRespVO.class));
    }

}
