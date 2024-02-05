package com.creator.esbatis.spring.boot.test.dao;

import com.creator.esbatis.spring.boot.test.entity.User;

public interface IUserDao {
    User queryUserInfoById(Long id);
}
