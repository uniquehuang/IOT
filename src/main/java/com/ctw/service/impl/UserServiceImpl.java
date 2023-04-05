package com.ctw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctw.entity.User;
import com.ctw.mapper.UserMapper;
import com.ctw.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author ctw
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-06-04 13:16:29
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

}
