package com.wang.dao;

import com.wang.entity.SecKill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 9:29
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */

/**
 * 配置spring和junit整合，junit启动时加载springIOC容器
 * spring-test,junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现依赖
    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        SecKill secKill = seckillDao.queryById(id);

        System.out.println(secKill.getName());
        System.out.println(secKill);
    }

    @Test
    public void testQueryAll() throws Exception {
        //java没有保存形参的记录：queryAll(int offser,int limit) ->queryAll(arg0,arg1)
        List<SecKill> secKills = seckillDao.queryAll(0, 100);
        for (SecKill secKill : secKills
                ) {
            System.out.println(secKill);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }
}