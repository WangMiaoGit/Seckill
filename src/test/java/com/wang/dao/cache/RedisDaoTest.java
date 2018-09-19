package com.wang.dao.cache;

import com.wang.dao.SeckillDao;
import com.wang.dto.Exposer;
import com.wang.dto.SeckillExecution;
import com.wang.entity.SecKill;
import com.wang.service.SecKillService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 2017063001 on 2018/9/18.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/18 17:52
 * 修改人  ：2017063001
 * 修改时间：2018/9/18
 * 修改备注：
 */

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest{

    private long id = 1024;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception{
        SecKill secKill = redisDao.getSeckilkll(id);
        if (secKill == null){
            secKill = seckillDao.queryById(id);
            if (secKill!=null){
                String result = redisDao.putSeckill(secKill);
                System.out.println(result);

                secKill = redisDao.getSeckilkll(id);
                System.out.println(secKill);
            }
        }
    }

}