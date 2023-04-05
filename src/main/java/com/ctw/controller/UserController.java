package com.ctw.controller;

import com.ctw.entity.Token;
import com.ctw.entity.User;
import com.ctw.interceptor.FreeTokenAuthentication;
import com.ctw.interceptor.RoleAuthentication;
import com.ctw.service.Result;
import com.ctw.service.UserManageService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户表控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserManageService userManageService;

    public UserController(UserManageService userManageService) {
        this.userManageService = userManageService;
    }

    @PostMapping("/login")
    @ResponseBody
    @FreeTokenAuthentication
    public Result login(User user) {
        return userManageService.login(user);
    }

    @PostMapping("/register")
    @ResponseBody
    @FreeTokenAuthentication
    public Result register(User user) {
        return userManageService.addUser(user);
    }

    @PostMapping("/login/check")
    @ResponseBody
    @FreeTokenAuthentication
    public Result checkLogin(String tokenValue) {
        return userManageService.checkLogin(tokenValue);
    }

    @PostMapping("/logout")
    @ResponseBody
    @FreeTokenAuthentication
    public Result logout(String tokenValue) {
        return userManageService.logout(tokenValue);
    }

    @GetMapping("/modify/info")
    @ResponseBody
    @RoleAuthentication
    public Result modifyUserInfo() {
        return new Result();
    }

    @GetMapping("/manage")
    @ResponseBody
    @RoleAuthentication(role = Token.Type.TT_ADMIN)
    public Result manageUsers() {
        return new Result();
    }

    @GetMapping("/update/version")
    @ResponseBody
    @RoleAuthentication(role = Token.Type.TT_DEVELOP)
    public Result updateSoftwareVersion() {
        return new Result();
    }
}