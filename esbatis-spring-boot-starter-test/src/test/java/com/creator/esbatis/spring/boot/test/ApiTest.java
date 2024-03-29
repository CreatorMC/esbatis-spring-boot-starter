package com.creator.esbatis.spring.boot.test;

import com.creator.esbatis.spring.boot.test.dao.IUserDao;
import com.creator.esbatis.spring.boot.test.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IUserDao userDao;

    @Test
    public void test_queryUserInfoById() {
        Article article = userDao.queryUserInfoById(3L);
        System.out.println(article.toString());
    }

}
