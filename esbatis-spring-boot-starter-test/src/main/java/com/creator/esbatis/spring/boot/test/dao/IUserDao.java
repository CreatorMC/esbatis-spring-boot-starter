package com.creator.esbatis.spring.boot.test.dao;

import com.creator.esbatis.spring.boot.test.entity.Article;

public interface IUserDao {
    Article queryUserInfoById(Long id);
}
