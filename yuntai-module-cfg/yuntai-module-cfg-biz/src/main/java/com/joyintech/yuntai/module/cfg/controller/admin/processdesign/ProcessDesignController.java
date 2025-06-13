package com.joyintech.yuntai.module.cfg.controller.admin.processdesign;

import cn.hutool.core.collection.CollUtil;
import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.node.Node;
import com.joyintech.yuntai.module.cfg.dal.dataobject.pageinfo.PageInfoDO;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processnode.ProcessNodeDO;
import com.joyintech.yuntai.module.cfg.service.pageinfo.PageInfoService;
import io.swagger.v3.oas.annotations.Parameters;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.annotation.security.PermitAll;
import javax.validation.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.joyintech.yuntai.framework.common.pojo.PageParam;
import com.joyintech.yuntai.framework.common.pojo.PageResult;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.common.util.object.BeanUtils;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

import com.joyintech.yuntai.framework.excel.core.util.ExcelUtils;

import com.joyintech.yuntai.framework.apilog.core.annotation.ApiAccessLog;
import static com.joyintech.yuntai.framework.apilog.core.enums.OperateTypeEnum.*;

import com.joyintech.yuntai.module.cfg.controller.admin.processdesign.vo.*;
import com.joyintech.yuntai.module.cfg.dal.dataobject.processdesign.ProcessDesignDO;
import com.joyintech.yuntai.module.cfg.service.processdesign.ProcessDesignService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 流程设计")
@RestController
@RequestMapping("/cfg/process-design")
@Validated
public class ProcessDesignController {

    @Resource
    private ProcessDesignService processDesignService;
    @Resource
    private PageInfoService pageInfoService;



    @PostMapping("/create")
    @Operation(summary = "创建流程设计")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:create')")
    public CommonResult<Long> createProcessDesign(@Valid @RequestBody ProcessDesignSaveReqVO createReqVO) {
        return success(processDesignService.createProcessDesign(createReqVO));
    }

    @PostMapping("/update")
    @Operation(summary = "更新流程设计")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:update')")
    public CommonResult<Boolean> updateProcessDesign(@Valid @RequestBody ProcessDesignSaveReqVO updateReqVO) {
        processDesignService.updateProcessDesign(updateReqVO);
        return success(true);
    }

    @PostMapping("/delete")
    @Operation(summary = "删除流程设计")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cfg:process-design:delete')")
    public CommonResult<Boolean> deleteProcessDesign(@RequestParam("id") Long id) {
        processDesignService.deleteProcessDesign(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得流程设计")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:query')")
    public CommonResult<ProcessDesignSaveReqVO> getProcessDesign(@RequestParam("id") Long id) {
        ProcessDesignSaveReqVO processDesign = processDesignService.getProcessDesign(id);
        return success(processDesign);
    }

    @GetMapping("/page")
    @Operation(summary = "获得流程设计分页")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:query')")
    public CommonResult<PageResult<ProcessDesignRespVO>> getProcessDesignPage(@Valid ProcessDesignPageReqVO pageReqVO) {
        PageResult<ProcessDesignDO> pageResult = processDesignService.getProcessDesignPage(pageReqVO);
        PageResult<ProcessDesignRespVO> result = BeanUtils.toBean(pageResult, ProcessDesignRespVO.class);
        if (CollUtil.isNotEmpty(result.getList())){
            Set<Long> list = result.getList().stream().map(ProcessDesignRespVO::getPageId).collect(Collectors.toSet());
            List<PageInfoDO> pageList = pageInfoService.getPageList(list);
            if (CollUtil.isNotEmpty(pageList)){
                Map<Long, PageInfoDO> pageMap = pageList.stream().collect(Collectors.toMap(PageInfoDO::getId, pageInfoDO -> pageInfoDO));
                result.getList().forEach(item -> {
                    PageInfoDO pageInfo = pageMap.get(item.getPageId());
                    if (Objects.nonNull(pageInfo)) {
                        item.setPageName(pageInfo.getPageName());
                    }
                });
            }
        }
        return success(result);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出流程设计 Excel")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportProcessDesignExcel(@Valid ProcessDesignPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ProcessDesignDO> list = processDesignService.getProcessDesignPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "流程设计.xls", "数据", ProcessDesignRespVO.class,
                BeanUtils.toBean(list, ProcessDesignRespVO.class));
    }

    @GetMapping("/getInfo")
    @Operation(summary = "获得单个节点的全部内容")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cfg:process-design:query')")
    public CommonResult<Node> getProcessDesignInfo(@RequestParam(value = "nodeId", required = false) String nodeId, @RequestParam(value = "pageId", required = false) Long pageId,
            @RequestParam(value = "flowId", required = false) String flowId) {
        // 这个接口要优化下：
        //1.如果传了pageID，查询对应flowId，返回流程信息以及第一个节点信息
        //2.传flowId，返回流程信息以及第一个节点信息
        //3.传flowId和nodeId，返回流程信息，以及对应节点信息；
        //流程信息需包括流程名称等基本信息；
        // 这个没有flowId，但是要先根据pageId查到flowId，再根据flowId和参数nodeId查到节点信息；
        Node node = processDesignService.getNode(nodeId, pageId, flowId);
        return success(node);
    }

    @PostMapping("/importExcel")
    @Operation(summary = "导入节点权限")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PermitAll
    public CommonResult<Map<String, List<Map<String, String>>>> importExcel(@RequestParam("file") MultipartFile file,
                                                                            @RequestParam("id") Long id) {

        // 权限值
        Map<String, List<Map<String, String>>> maps = new HashMap<>();

        // 节点值
        List<String> listSort = new ArrayList<>();
        try (FileInputStream fis = (FileInputStream) file.getInputStream()) {
            // 创建 Workbook 对象，自动检测文件格式
            Workbook workbook = WorkbookFactory.create(fis);

            // 遍历所有工作表
            for (Sheet sheet : workbook) {
                System.out.println("工作表名称: " + sheet.getSheetName());
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    // 遍历行中的单元格
                    Iterator<Cell> cellIterator = row.cellIterator();
                    List<Map<String, String>> list = new ArrayList<>();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        Map<String, String> map1 = new HashMap<>();
                        // 根据单元格类型读取数据
                        switch (cell.getCellType()) {
                            case STRING:
                                if (row.getRowNum() == 0) {
                                    listSort.add(cell.getStringCellValue());
                                } else {
                                    if (cell.getColumnIndex() != 0) {
                                        map1.put(String.valueOf(row.getCell(0)), cell.getStringCellValue());
                                        list.add(map1);
                                        maps.put(String.valueOf(row.getCell(0)), list);
                                    }
                                }
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    System.out.print(cell.getDateCellValue() + "\t");
                                } else {
                                    System.out.print(cell.getNumericCellValue() + "\t");
                                }
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
                            case FORMULA:
                                System.out.print(cell.getCellFormula() + "\t");
                                break;
                            default:
                                System.out.print(" \t");
                        }
                    }
                    System.out.println(); // 换行
                }
            }
        } catch (IOException e) {
            System.err.println("文件读取错误: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("处理 Excel 时出错: " + e.getMessage());
        }
        return success(processDesignService.importExcel(id, listSort, maps));
    }

}
