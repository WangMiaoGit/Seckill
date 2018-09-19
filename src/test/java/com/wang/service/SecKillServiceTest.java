package com.wang.service;

import com.wang.dto.Exposer;
import com.wang.dto.SeckillExecution;
import com.wang.entity.SecKill;
import com.wang.exception.RepeatKillException;
import com.wang.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by 2017063001 on 2018/9/4.
 * 项目名称：Seckill
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2018/9/4 15:21
 * 修改人  ：2017063001
 * 修改时间：2018/9/4
 * 修改备注：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SecKillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SecKillService secKillService;

    @Test
    public void testGetSeckillList() throws Exception {
        List<SecKill> list = secKillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void testGetById() throws Exception {
        SecKill secKill = secKillService.getById(1000);
        logger.info("seckill={}", secKill);
    }

    //集成测试代码完整逻辑，可重复执行
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001L;
        Exposer exposer = secKillService.exportSeckillUrl(id);

        if (exposer.isExposed()) {

            logger.info("exposer={}", exposer);
            long phone = 13562186425L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = secKillService.executeSeckill(id, phone, md5);
                logger.info("result={}", seckillExecution.toString());
                //result=com.wang.dto.SeckillExecution@74bada02
            } catch (RepeatKillException | SeckillCloseException e) {
                logger.error(e.getMessage(), e);
            }

        } else {
            //秒杀未开启
            logger.warn("exposer={}", exposer);
        }
        //Exposer{
        // exposed=true,
        // md5='a85464c0e3a5aa6391f25e311a593e95',
        // seckillId=1000,
        // now=0, start=0, end=0}
    }



    @Test
    public void executeSeckillProcedure(){
        long seckillId=1024;
        long phone = 1380000001;

        Exposer exposer = secKillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5 =exposer.getMd5();
            SeckillExecution execution = secKillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }
    }


    @Test
    public void testExecuteSeckill() throws Exception {
      /*  long id = 1000L;
        long phone = 13562186425L;
        String md5 = "a85464c0e3a5aa6391f25e311a593e95";
        try {
            SeckillExecution seckillExecution = secKillService.executeSeckill(id, phone, md5);
            logger.info("result={}", seckillExecution.toString());
            //result=com.wang.dto.SeckillExecution@74bada02
        } catch (RepeatKillException e) {
            logger.error(e.getMessage(), e);
        } catch (SeckillCloseException e2) {
            logger.error(e2.getMessage(), e2);
        }*/

    }
}