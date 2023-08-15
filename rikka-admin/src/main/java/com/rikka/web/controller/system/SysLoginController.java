package com.rikka.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.rikka.common.core.domain.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.rikka.common.constant.Constants;
import com.rikka.common.core.domain.AjaxResult;
import com.rikka.common.core.domain.entity.SysMenu;
import com.rikka.common.core.domain.entity.SysUser;
import com.rikka.common.core.domain.model.LoginBody;
import com.rikka.common.utils.SecurityUtils;
import com.rikka.framework.web.service.SysLoginService;
import com.rikka.framework.web.service.SysPermissionService;
import com.rikka.system.service.ISysMenuService;

import static com.rikka.common.core.domain.AjaxResult.success;

/**
 * 登录验证
 * 
 * @author rikka
 */
@RestController
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    /**
     * 登录方法
     * 
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
        Map<String, Object> result = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        result.put(Constants.TOKEN, token);
        result.put("expire", expireTime * 60);
        return success(result);
    }

    /**
     * 获取用户信息
     * 
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        user.setPassword(null);
        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
//        Set<String> permissions = permissionService.getMenuPermission(user);
//        Map<String, Object> result = new HashMap<>();
//        result.put("user", user);
//        result.put("roles", roles);
//        result.put("permissions", permissions);
        return success(user);
    }

    /**
     * 获取路由信息
     * 
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(loginUser.getUserId());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(loginUser.getUser());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(loginUser.getUser());

        Map<String, Object> result = new HashMap<>();
        result.put("menus", menuService.buildMenus(menus));
        result.put("perms", permissions);
        result.put("roles", roles);

        return success(result);
    }
}
