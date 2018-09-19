package com.wang.dao;

import com.wang.entity.SuccessKilled;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 10:23
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id=1000L;
        long phone = 13662231664L;
        int insertCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("insertCount = "+ insertCount);
    }

    @Test
    public void testQueryByIdWithSeckill() throws Exception {
        long id=1000L;
        long phone = 13662231664L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);
        System.out.println("successKilled="+successKilled);
        System.out.println(successKilled.getSecKill());
    }
}