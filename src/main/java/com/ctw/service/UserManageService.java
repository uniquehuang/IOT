package com.ctw.service;

import com.ctw.dao.UserDao;
import com.ctw.entity.Token;
import com.ctw.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cjq
 * @program: TokenDemo
 * @description: 为用户模块相关操作提供服务
 * @date 2021-05-07 20:41:46
 */
@Service
public class UserManageService {

    private  UserDao userDao;
    private  TokenManager tokenManager;

    public UserManageService(UserDao userDao, TokenManager tokenManager) {
        this.userDao = userDao;
        this.tokenManager = tokenManager;
    }

    public Result login(User user) {
        Result result = new Result();
        //判断用户名是否存在
        List<User> users = userDao.findUser(user);
        if (users == null || users.isEmpty()) {
            result.setStatus(1);
            result.setMsg("此用户不存在，请先进行注册");
            return result;
        }
        //判断用户密码是否正确
        User foundUser = users.get(0);
        if (!foundUser.getPassword().equals(user.getPassword())) {
            result.setStatus(2);
            result.setMsg("密码错误");
            return result;
        }
        result.setStatus(3);
        result.setMsg("登录成功");
        //创建token并返回
        Token token = tokenManager.createToken((Integer) foundUser.getId(),
                Token.Type.values()[foundUser.getRole()]);
        result.setData(token.getValue());
        return result;
    }

    public Result addUser(User user) {
        Result result = new Result();
        //判断用户名是否存在
        List<User> users = userDao.findUser(user);
        if (users != null && !users.isEmpty()) {
            result.setStatus(0);
            result.setMsg("用户名已存在，请直接登录或者更换用户名");
            return result;
        }
        //添加用户
        int affectedRowCount = userDao.addUser(user);
        if (affectedRowCount == 1) {
            result.setStatus(1);
            result.setMsg("添加用户成功");
        } else {
            result.setStatus(2);
            result.setMsg("添加用户失败");
        }
        return result;
    }

    public Result checkLogin(String tokenValue) {
        Result result = new Result();
        Token token = new Token(tokenValue);
        if (tokenManager.checkToken(token)) {
            result.setStatus(0);
            result.setMsg(token.getType().getName() + "，已登录");
        } else {
            result.setStatus(1);
            result.setMsg("未登录");
        }
        return result;
    }

    public Result logout(String tokenValue) {
        Result result = new Result();
        Token token = new Token(tokenValue);
        tokenManager.deleteToken(token);
        result.setMsg("未登录");
        return result;
    }
}
