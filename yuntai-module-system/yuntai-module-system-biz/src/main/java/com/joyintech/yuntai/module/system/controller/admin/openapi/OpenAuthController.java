package com.joyintech.yuntai.module.system.controller.admin.openapi;

import java.util.Optional;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.module.system.controller.admin.auth.vo.AuthLoginRespVO;
import com.joyintech.yuntai.module.system.controller.admin.openapi.vo.OpenAuthLoginReqVO;
import com.joyintech.yuntai.module.system.enums.logger.LoginLogTypeEnum;
import com.joyintech.yuntai.module.system.service.auth.AdminAuthServiceImpl;
import com.joyintech.yuntai.module.system.service.user.AdminUserService;
import com.joyintech.yuntai.module.system.sysuser.mapper.SysDeptMapper;
import com.joyintech.yuntai.module.system.sysuser.model.SysDepart;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.SysUserService;
import com.joyintech.yuntai.module.system.util.IdentityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import static com.joyintech.yuntai.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;
import static com.joyintech.yuntai.module.system.enums.ErrorCodeConstants.*;

@Tag(name = "外部系统 - 认证")
@RestController
@RequestMapping("/system/openAuth")
@Validated
@Slf4j
public class OpenAuthController {

    @Resource
    private AdminUserService userService;

    @Resource
    private AdminAuthServiceImpl adminAuthService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Value("${external.secret-key:''}")
    private String secretKey;

    @Value("${fanwei.userName}")
    private String configUserName;


    @GetMapping("/openLogin")
    @PermitAll
    @Operation(summary = "使用账号登录")
    public CommonResult<AuthLoginRespVO> login(@RequestParam String userName) throws Exception {

        // 校验账号是否存在
        SysUser user = sysUserService.getUserByUsername(userName);
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }

        // 创建 Token 令牌，记录登录日志TODO：西部项目暂时写死用户权限
        AuthLoginRespVO authLoginRespVO = adminAuthService.createTokenAfterLoginSuccess(user.getId(), user.getUsername(), LoginLogTypeEnum.OPEN_LOGIN_USERNAME);

        authLoginRespVO.setUserId(user.getId());
        authLoginRespVO.setUserName(user.getUsername());
        authLoginRespVO.setRealName(user.getRealname());
        authLoginRespVO.setDepartId(user.getDepartId());
        authLoginRespVO.setWorkNo(user.getWorkNo());
        SysDepart sysDepart = sysDeptMapper.selectById(user.getDepartId());
        if(Optional.ofNullable(sysDepart).isPresent()){
            authLoginRespVO.setDepartName(sysDepart.getDepartName());
        }

        return success(authLoginRespVO);
    }

    @PostMapping("/getToken")
    @PermitAll
    @Operation(summary = "获取token")
    public CommonResult<AuthLoginRespVO> getToken(@RequestBody OpenAuthLoginReqVO openAuthLoginReqVO) {
        String appId = "01";
        String appSource = "OA";
        if (!appId.equals(openAuthLoginReqVO.getAppId())) {
            throw exception(AUTH_OPENLOGIN_APPID_ERROR);
        }

        if (!appSource.equals(openAuthLoginReqVO.getAppSource())) {
            throw exception(AUTH_OPENLOGIN_APPSOURCE_ERROR);
        }

        // 校验账号是否存在
        SysUser user = sysUserService.getUserByUsername(openAuthLoginReqVO.getUserName());
        if (user == null) {
            throw exception(AUTH_OPENLOGIN_CODE_ERROR);
        }

        // 创建 Token 令牌，记录登录日志TODO：西部项目暂时写死用户权限
        return success(adminAuthService.createTokenAfterLoginSuccess(1356921330604666888L, "xbxt", LoginLogTypeEnum.OPEN_LOGIN_USERNAME));
    }

    @GetMapping("/openLoginFanWeiOA")
    @Operation(summary = "使用账号登录")
    @PermitAll
    public CommonResult<AuthLoginRespVO> openLoginFanWeiOA(@RequestParam String userName) {
        AuthLoginRespVO authLoginRespVO = new AuthLoginRespVO();
        userName = configUserName;
        // 校验账号是否存在
        SysUser user = sysUserService.getUserByUsername(userName);
        if (user == null) {
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        String token = IdentityUtil.getToken(user);
        return success(authLoginRespVO.setAccessToken(token));
    }
}
