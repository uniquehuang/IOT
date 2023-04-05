package com.ctw.dao;



import com.ctw.entity.User;

import java.util.List;

public interface UserDao {
    int addUser(User user);
    List<User> findUser(User user);
    int updateUserInfo(User user);
    int deleteUser(User user);
}
