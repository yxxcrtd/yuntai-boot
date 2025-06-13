package com.joyintech.yuntai.module.cfg.controller.admin.businessinterface;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.excel.core.util.MyAnalysisEventListener;
import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.enums.ExcelTemplateEnum;
import com.joyintech.yuntai.module.cfg.controller.admin.businessinterface.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;

/**
 *
 * 业务定制化接口
 * @author hzz
 * @since 2025-03-07
 *
 */
@Tag(name = "业务接口 - 定制化")
@RestController
@RequestMapping("/cfg/business")
@Validated
public class BusinessController {

    @PostMapping("/importExcel")
    @Operation(summary = "导入标品数据")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PermitAll
    public CommonResult<Map<String, List<?>>> importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        //文件校验
        String filename = file.getOriginalFilename();
        if (file.isEmpty() || filename == null) {
            throw exception(500,"请选择文件");
        }
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        if (!suffix.equalsIgnoreCase("xls") && !suffix.equalsIgnoreCase("xlsx")) {
            throw exception(500,"文件格式不正确");
        }

        // 根据文件名获取实体类
        Class<?> entityClass = ExcelTemplateEnum.getEntityClassByFileName(filename);

        Map<String, List<?>> map = new HashMap<>();
        switch (filename) {
            case "信评资产新增模板.xlsx":
                // 主体
                MyAnalysisEventListener<Object> myAnalysisEventListener = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<CreditRatingAssetReqVO> dataList = EasyExcel.read(inputStream)
                            .head(CreditRatingAssetReqVO.class)
                            .sheet(0)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setASSET_NO("AP" + "-" + LocalDate.now().getYear() + "-" + Arrays.toString(getRandom().split(",")));
                            s.setASSET_TYPE("信评债主体池");
                        });
                    }
                    map.put(myAnalysisEventListener.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（信评债主体池）失败");
                }

                // 同业
                MyAnalysisEventListener<Object> myAnalysisEventListener1 = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<CreditRatingAssetProfessionReqVO> dataList = EasyExcel.read(inputStream)
                            .head(CreditRatingAssetProfessionReqVO.class)
                            .sheet(1)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener1)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setASSET_NO("AP" + "-" + LocalDate.now().getYear() + "-" + Arrays.toString(getRandom().split(",")));
                            s.setASSET_TYPE("同业存单");
                        });
                    }
                    map.put(myAnalysisEventListener1.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（同业存单）失败");
                }

                // 证劵
                MyAnalysisEventListener<Object> myAnalysisEventListener2 = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<CreditRatingAssetSecurityReqVO> dataList = EasyExcel.read(inputStream)
                            .head(CreditRatingAssetSecurityReqVO.class)
                            .sheet(2)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener2)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setASSET_NO("AP" + "-" + LocalDate.now().getYear() + "-" + Arrays.toString(getRandom().split(",")));
                            s.setASSET_TYPE("证券公司债");
                        });
                    }
                    map.put(myAnalysisEventListener2.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（证券公司债）失败");
                }
                break;
            case "信评资产变更模板.xlsx":
                // 主体
                MyAnalysisEventListener<Object> myAnalysisEventListener3 = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<UpdateRatingAssetReqVO> dataList = EasyExcel.read(inputStream)
                            .head(UpdateRatingAssetReqVO.class)
                            .sheet(0)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener3)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setAssetType("信评债主体池");
                        });
                    }
                    map.put(myAnalysisEventListener3.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（信评债主体池）失败");
                }

                // 同业
                MyAnalysisEventListener<Object> myAnalysisEventListener4 = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<UpdateRatingAssetProfessionReqVO> dataList = EasyExcel.read(inputStream)
                            .head(UpdateRatingAssetProfessionReqVO.class)
                            .sheet(1)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener4)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setAssetType("同业存单");
                        });
                    }
                    map.put(myAnalysisEventListener4.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（同业存单）失败");
                }

                // 证劵
                MyAnalysisEventListener<Object> myAnalysisEventListener5 = new MyAnalysisEventListener<>();
                try (InputStream inputStream = file.getInputStream()) {
                    List<UpdateRatingAssetSecurityReqVO> dataList = EasyExcel.read(inputStream)
                            .head(UpdateRatingAssetSecurityReqVO.class)
                            .sheet(2)
                            .headRowNumber(2)
                            .registerReadListener(myAnalysisEventListener5)
                            .doReadSync();
                    if (CollUtil.isNotEmpty(dataList)) {
                        dataList.forEach(s -> {
                            s.setAssetType("证券公司债");
                        });
                    }
                    map.put(myAnalysisEventListener5.getSheetName(), dataList);
                } catch (IOException e) {
                    throw exception(500,"导入文件（证券公司债）失败");
                }
                break;
            case "公募资管产品新增模板.xlsx":
                System.out.println(111);
                break;
            case "公募资管产品变更模板.xlsx":
                System.out.println(111);
                break;
            case "可转债可交换债新增模板.xlsx":
                System.out.println(111);
                break;
            case "可转债可交换债变更模板.xlsx":
                System.out.println(111);
                break;
            case "私募资管产品新增模板.xlsx":
                System.out.println(111);
                break;
            case "私募资管产品变更模板.xlsx":
                System.out.println(111);
                break;
        }
        return success(map);
    }

    private static String getRandom() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        //每次随机生成一个数字，循环5次(下标从0开始)得到6个随机数，保存到数组里
        for(int i = 0; i <= 5; i++) {
            //只需要0-9之间的随机6位，所以nextInt(9)里就生成到9就好了，包含0在里面
            list.add(random.nextInt(9));
        }
        return list.toString();
    }
}
