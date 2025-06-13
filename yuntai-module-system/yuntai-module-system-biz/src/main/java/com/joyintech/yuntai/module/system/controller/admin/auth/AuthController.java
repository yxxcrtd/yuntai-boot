package com.joyintech.yuntai.module.system.controller.admin.auth;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.joyintech.yuntai.framework.common.enums.CommonStatusEnum;
import com.joyintech.yuntai.framework.common.enums.UserTypeEnum;
import com.joyintech.yuntai.framework.common.pojo.CommonResult;
import com.joyintech.yuntai.framework.security.config.SecurityProperties;
import com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils;
import com.joyintech.yuntai.module.system.controller.admin.auth.vo.*;
import com.joyintech.yuntai.module.system.convert.auth.AuthConvert;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.MenuDO;
import com.joyintech.yuntai.module.system.dal.dataobject.permission.RoleDO;
import com.joyintech.yuntai.module.system.dal.dataobject.user.AdminUserDO;
import com.joyintech.yuntai.module.system.enums.logger.LoginLogTypeEnum;
import com.joyintech.yuntai.module.system.service.auth.AdminAuthService;
import com.joyintech.yuntai.module.system.service.permission.MenuService;
import com.joyintech.yuntai.module.system.service.permission.PermissionService;
import com.joyintech.yuntai.module.system.service.permission.RoleService;
import com.joyintech.yuntai.module.system.service.social.SocialClientService;
import com.joyintech.yuntai.module.system.service.syspermission.SysPermissionService;
import com.joyintech.yuntai.module.system.service.user.AdminUserService;
import com.joyintech.yuntai.module.system.sysuser.model.SysUser;
import com.joyintech.yuntai.module.system.sysuser.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.joyintech.yuntai.framework.common.pojo.CommonResult.success;
import static com.joyintech.yuntai.framework.common.util.collection.CollectionUtils.convertSet;
import static com.joyintech.yuntai.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 认证")
@RestController
@RequestMapping("/system/auth")
@Validated
@Slf4j
public class AuthController {

    @Resource
    private AdminAuthService authService;
    @Resource
    private AdminUserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private MenuService menuService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private SocialClientService socialClientService;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private SysUserService sysUserService;

    @PostMapping("/login")
    @PermitAll
    @Operation(summary = "使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(authService.login(reqVO));
    }

    @PostMapping("/logout")
    @PermitAll
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
                securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotBlank(token)) {
            authService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @PostMapping("/refresh-token")
    @PermitAll
    @Operation(summary = "刷新令牌")
    @Parameter(name = "refreshToken", description = "刷新令牌", required = true)
    public CommonResult<AuthLoginRespVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return success(authService.refreshToken(refreshToken));
    }

    @GetMapping("/get-permission-info")
    @Operation(summary = "获取登录用户的权限信息")
    public CommonResult<AuthPermissionInfoRespVO> getPermissionInfo(@RequestParam(value = "moduleType",required = false) Integer moduleType) {
        // 1.1 获得用户信息
        Long userId = getLoginUserId();
        if(getLoginUserId() == null){
            return success(null);
        }
        SysUser sysUser = sysUserService.getUser(userId.toString());
        if (sysUser == null) {
            return success(null);
        }
        AdminUserDO user = new AdminUserDO();
        user.setId(userId).setUsername(sysUser.getUsername()).setPassword(sysUser.getPassword())
                .setDeptId(Long.valueOf(sysUser.getDepartId()))
                .setNickname(sysUser.getRealname());
        // 1.2 获得角色列表
        Set<Long> roleIds = permissionService.getUserRoleIdListByUserId(getLoginUserId());
        if (CollUtil.isEmpty(roleIds)) {
            return success(AuthConvert.INSTANCE.convert(user, Collections.emptyList(), Collections.emptyList(), Collections.emptySet()));
        }
        List<RoleDO> roles = roleService.getRoleList(roleIds);
        roles.removeIf(role -> !CommonStatusEnum.ENABLE.getStatus().equals(role.getStatus())); // 移除禁用的角色

        // 1.3 获得菜单列表
        Set<Long> menuIds = permissionService.getRoleMenuListByRoleId(convertSet(roles, RoleDO::getId));
        List<MenuDO> menuList = menuService.getMenuList(menuIds);
        menuList = menuService.filterDisableMenus(menuList);
        if (moduleType != null) {
            menuList = menuList.stream().filter(m -> Objects.equals(m.getModuleType(), moduleType)).collect(Collectors.toList());
        }

        // permissions 按钮的权限从另一张表取
        // TODO 这里从新表获取用户角色、权限的方法，后期还得改。因为新表的userid是String型，不是Long。这里只是permissions的取值，改成取新表
        Set<String> roleIdsNew = sysPermissionService.getUserRoleIdListByUserId(String.valueOf(getLoginUserId()));
//        Set<String> roleIdsNew = sysPermissionService.getUserRoleIdListByUserId("c95a5f05-6a21-4f32-8814-9a7f4309143c");
        if (CollUtil.isEmpty(roleIdsNew)) {
            return success(AuthConvert.INSTANCE.convert(user, roles, menuList, Collections.emptySet()));
        }
        Set<String> perms = sysPermissionService.getRoleMenuListByRoleId(roleIdsNew);
        user.setWorkNo(sysUser.getWorkNo());

        // 2. 拼接结果返回
        return success(AuthConvert.INSTANCE.convert(user, roles, menuList, perms));
    }

    // ========== 短信登录相关 ==========

    @PostMapping("/sms-login")
    @PermitAll
    @Operation(summary = "使用短信验证码登录")
    public CommonResult<AuthLoginRespVO> smsLogin(@RequestBody @Valid AuthSmsLoginReqVO reqVO) {
        return success(authService.smsLogin(reqVO));
    }

    @PostMapping("/send-sms-code")
    @PermitAll
    @Operation(summary = "发送手机验证码")
    public CommonResult<Boolean> sendLoginSmsCode(@RequestBody @Valid AuthSmsSendReqVO reqVO) {
        authService.sendSmsCode(reqVO);
        return success(true);
    }

    // ========== 社交登录相关 ==========

    @GetMapping("/social-auth-redirect")
    @PermitAll
    @Operation(summary = "社交授权的跳转")
    @Parameters({
            @Parameter(name = "type", description = "社交类型", required = true),
            @Parameter(name = "redirectUri", description = "回调路径")
    })
    public CommonResult<String> socialLogin(@RequestParam("type") Integer type,
                                            @RequestParam("redirectUri") String redirectUri) {
        return success(socialClientService.getAuthorizeUrl(
                type, UserTypeEnum.ADMIN.getValue(), redirectUri));
    }

    @PostMapping("/social-login")
    @PermitAll
    @Operation(summary = "社交快捷登录，使用 code 授权码", description = "适合未登录的用户，但是社交账号已绑定用户")
    public CommonResult<AuthLoginRespVO> socialQuickLogin(@RequestBody @Valid AuthSocialLoginReqVO reqVO) {
        return success(authService.socialLogin(reqVO));
    }

}
